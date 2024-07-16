package org.yosua.binfood.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yosua.binfood.model.entity.AuthenticationProvider;
import org.yosua.binfood.model.entity.Role;
import org.yosua.binfood.model.entity.User;
import org.yosua.binfood.model.dto.request.LoginUserRequest;
import org.yosua.binfood.model.dto.request.RegisterUserRequest;
import org.yosua.binfood.model.dto.response.JwtResponse;
import org.yosua.binfood.model.dto.response.UserResponse;
import org.yosua.binfood.repositories.RoleRepository;
import org.yosua.binfood.repositories.UserRepository;
import org.yosua.binfood.services.AuthService;
import org.yosua.binfood.services.JwtService;
import org.yosua.binfood.services.TokenService;
import org.yosua.binfood.services.ValidationService;
import org.yosua.binfood.utils.Roles;

import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ValidationService validationService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;

    @Autowired
    public AuthServiceImpl(
            UserRepository userRepository,
            ValidationService validationService,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository,
            TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.validationService = validationService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.tokenService = tokenService;
    }

    @Transactional
    @Override
    public UserResponse register(RegisterUserRequest request) {
        validationService.validate(request);

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password and confirm password must be the same");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered");
        }

        User user = createAndSaveUser(request.getFullName(), request.getEmail(), request.getPassword(), AuthenticationProvider.LOCAL);

        return createUserResponse(user);
    }

    /**
     * This method is used to authenticate a user and generate a JWT token for the user.
     * It first validates the request, then it checks if the user exists in the database.
     * If the user exists, it checks if the password matches the one stored in the database.
     * If the password matches, it authenticates the user using Spring Security's AuthenticationManager.
     * After successful authentication, it generates a JWT token for the user and saves it in the database.
     * Finally, it returns a JwtResponse containing the JWT token, refresh token, username, and roles of the user.
     *
     * @param request This is a request object which contains the username and password of the user.
     * @return JwtResponse This returns a response object containing the JWT token, refresh token, username, and roles of the user.
     * @throws ResponseStatusException This exception is thrown if the username is not found in the database or the password does not match.
     */
    @Transactional
    @Override
    public JwtResponse login(LoginUserRequest request) {
        // Validate the request
        validationService.validate(request);

        // Check if the user exists in the database
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password wrong"));

        // Check if the password matches the one stored in the database
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or password wrong");
        }

        // Authenticate the user using Spring Security's AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Set the authentication in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return createJwtResponse(user);
    }

    @Override
    public UserResponse registerOAuth2User(DefaultOAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        User user = createAndSaveUser(name, email, null, AuthenticationProvider.GOOGLE);

        return createUserResponse(user);
    }

    @Override
    public JwtResponse loginOAuth2User(DefaultOAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Something Wrong"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return createJwtResponse(user);
    }

    private User createAndSaveUser(String fullName, String email, String password, AuthenticationProvider authenticationProvider) {
        String role = String.valueOf(Roles.ROLE_USER);
        Role userRole = roleRepository.findByName(role)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found"));

        List<Role> authorities = new ArrayList<>();
        authorities.add(userRole);

        User user = User.builder()
                .fullName(fullName)
                .email(email)
                .authenticationProvider(authenticationProvider)
                .roles(authorities)
                .isActive(true)
                .build();

        if (authenticationProvider == AuthenticationProvider.LOCAL) {
            user.setPassword(passwordEncoder.encode(password));
        }

        return userRepository.save(user);
    }

    private UserResponse createUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .roles(getRoles(user))
                .build();
    }

    private List<String> getRoles(User user) {
        // Get the roles of the user
        return user.getRoles().stream().map(Role::getName).toList();
    }

    private JwtResponse createJwtResponse(User user) {
        // Generate a JWT token for the user (5 minutes)
        String jwtToken = jwtService.generateToken(user);

        // Create a new refresh token for the user (10 minutes)
        String refreshToken = tokenService.createRefreshToken();

        tokenService.createToken(user.getEmail(), jwtToken, refreshToken);

        // Return a JwtResponse containing the JWT token, refresh token, username, and roles of the user
        return JwtResponse.builder()
                .email(user.getEmail())
                .roles(getRoles(user))
                .jwtToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
}
