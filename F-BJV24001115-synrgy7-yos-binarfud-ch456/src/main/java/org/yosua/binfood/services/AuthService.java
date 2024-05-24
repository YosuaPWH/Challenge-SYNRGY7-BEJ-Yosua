package org.yosua.binfood.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yosua.binfood.model.entity.User;
import org.yosua.binfood.model.request.LoginUserMerchantRequest;
import org.yosua.binfood.model.response.TokenResponse;
import org.yosua.binfood.repository.UserRepository;
import org.yosua.binfood.security.BCrypt;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ValidationService validationService;

    @Autowired
    public AuthService(UserRepository userRepository, ValidationService validationService) {
        this.userRepository = userRepository;
        this.validationService = validationService;
    }

    @Transactional
    public TokenResponse login(LoginUserMerchantRequest request) {
        validationService.validate(request);

        User user = userRepository.findFirstByEmailAddress(request.getEmailAddress())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong"));

        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong");
        }

        user.setToken(UUID.randomUUID().toString());
        user.setTokenExpiredAt(next1Hour());
        userRepository.save(user);

        return TokenResponse.builder()
                .token(user.getToken())
                .build();
    }

    @Transactional
    public void logout(User user) {
        user.setToken(null);
        user.setTokenExpiredAt(null);

        userRepository.save(user);
    }

    private Long next1Hour() {
        return System.currentTimeMillis() + (1000 * 60 * 60);
    }
}
