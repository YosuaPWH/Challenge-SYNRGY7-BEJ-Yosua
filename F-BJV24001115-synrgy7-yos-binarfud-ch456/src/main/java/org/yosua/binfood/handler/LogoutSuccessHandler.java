package org.yosua.binfood.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.yosua.binfood.repositories.TokenRepository;

@Service
public class LogoutSuccessHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Autowired
    public LogoutSuccessHandler(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            tokenRepository.deleteByJwtToken(jwt);
        }
    }
}
