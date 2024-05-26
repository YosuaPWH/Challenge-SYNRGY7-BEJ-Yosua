package org.yosua.binfood.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yosua.binfood.model.entity.Role;
import org.yosua.binfood.model.entity.User;
import org.yosua.binfood.model.request.ChangePasswordRequest;
import org.yosua.binfood.model.request.UpdateUserRequest;
import org.yosua.binfood.model.response.UserResponse;
import org.yosua.binfood.repositories.UserRepository;
import org.yosua.binfood.utils.Constants;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final ValidationService validationService;
    private final PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(
            UserRepository userRepository,
            ValidationService validationService,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public UserResponse authenticatedUser() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return UserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .emailAddress(user.getEmailAddress())
                    .roles(user.getRoles().stream().map(Role::getName).toList())
                    .build();
        } catch (Exception e) {
            log.error("Failed to get authenticated user: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.GET_AUTHENTICATED_USER_FAILED);
        }
    }

    public void update(UpdateUserRequest request) {
        try {
            validationService.validate(request);

            if (userRepository.existsByUsername(request.getUsername())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already registered");
            }

            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            currentUser.setUsername(request.getUsername());
            userRepository.save(currentUser);
        } catch (Exception e) {
            log.error("Failed to update user: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.UPDATE_USER_FAILED);
        }
    }

    public void changePassword(ChangePasswordRequest request) {
        try {
            validationService.validate(request);

            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (!passwordEncoder.matches(currentUser.getPassword(), request.getCurrentPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is wrong");
            }

            currentUser.setPassword(request.getNewPassword());
            userRepository.save(currentUser);
        } catch (Exception e) {
            log.error("Failed to change password: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.CHANGE_PASSWORD_FAILED);
        }

    }
}
