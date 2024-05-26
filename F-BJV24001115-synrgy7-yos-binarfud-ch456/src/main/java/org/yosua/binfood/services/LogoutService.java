package org.yosua.binfood.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.yosua.binfood.repositories.UserRepository;

@Service
public class LogoutService implements LogoutHandler {

    private final UserRepository userRepository;

    @Autowired
    public LogoutService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            userRepository.findFirstByToken(jwt).ifPresent(user -> {
                user.setToken(null);
                userRepository.save(user);
            });
        }
    }
}
