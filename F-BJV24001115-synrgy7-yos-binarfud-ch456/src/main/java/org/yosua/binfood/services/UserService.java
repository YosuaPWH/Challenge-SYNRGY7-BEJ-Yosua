package org.yosua.binfood.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yosua.binfood.model.entity.User;
import org.yosua.binfood.model.request.RegisterUserMerchantRequest;
import org.yosua.binfood.model.response.UserResponse;
import org.yosua.binfood.repository.UserRepository;
import org.yosua.binfood.security.BCrypt;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final ValidationService validationService;

    @Autowired
    public UserService(UserRepository userRepository, ValidationService validationService) {
        this.userRepository = userRepository;
        this.validationService = validationService;
    }

    @Transactional
    public UserResponse register(RegisterUserMerchantRequest request) {
        validationService.validate(request);

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password and confirm password must be the same");
        }

        if (userRepository.existsByEmailAddress(request.getEmailAddress())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email address already registered");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already registered");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()))
                .emailAddress(request.getEmailAddress())
                .build();

        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .emailAddress(user.getEmailAddress())
                .username(user.getUsername())
                .build();

    }
}
