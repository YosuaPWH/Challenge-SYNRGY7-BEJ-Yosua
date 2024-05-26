package org.yosua.binfood.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yosua.binfood.model.entity.Role;
import org.yosua.binfood.model.entity.User;
import org.yosua.binfood.model.request.LoginUserRequest;
import org.yosua.binfood.model.request.RegisterUserRequest;
import org.yosua.binfood.model.response.JwtResponse;
import org.yosua.binfood.model.response.UserResponse;
import org.yosua.binfood.repositories.RoleRepository;
import org.yosua.binfood.repositories.UserRepository;
import org.yosua.binfood.utils.Roles;

import java.util.*;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ValidationService validationService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthService(
            UserRepository userRepository,
            ValidationService validationService,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.validationService = validationService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public UserResponse register(RegisterUserRequest request) {
        validationService.validate(request);

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password and confirm password must be the same");
        }

        if (userRepository.existsByEmailAddress(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email address already registered");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already registered");
        }

        String role = String.valueOf(Roles.ROLE_USER);
        Role userRole = roleRepository.findByName(role)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found"));

        List<Role> authorities = new ArrayList<>();
        authorities.add(userRole);

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .emailAddress(request.getEmail())
                .roles(authorities)
                .build();

        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .emailAddress(user.getEmailAddress())
                .username(user.getUsername())
                .roles(user.getRoles().stream().map(Role::getName).toList())
                .build();
    }

    @Transactional
    public JwtResponse login(LoginUserRequest request) {
        validationService.validate(request);

        User user = userRepository.findFirstByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String jwtToken = jwtService.generateToken(user);
        user.setToken(jwtToken);
        userRepository.save(user);

        return JwtResponse.builder()
                .token(user.getToken())
                .username(user.getUsername())
                .roles(roles)
                .build();
    }

    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User currentUser = (User) authentication.getPrincipal();
            currentUser.setToken(null);

            userRepository.save(currentUser);
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }
}
