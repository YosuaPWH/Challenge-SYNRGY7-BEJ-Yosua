package org.yosua.binfood.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yosua.binfood.model.entity.Token;
import org.yosua.binfood.model.dto.request.RefreshTokenRequest;
import org.yosua.binfood.model.dto.response.JwtResponse;
import org.yosua.binfood.model.entity.User;
import org.yosua.binfood.repositories.TokenRepository;
import org.yosua.binfood.repositories.UserRepository;
import org.yosua.binfood.services.JwtService;
import org.yosua.binfood.services.TokenService;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    @Autowired
    public TokenServiceImpl(
            UserRepository userRepository,
            TokenRepository tokenRepository,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
    }

    @Override
    public Token createToken(String email, String jwtToken, String refreshToken) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Token token = Token.builder()
                .user(user)
                .jwtToken(jwtToken)
                .refreshToken(refreshToken)
                .expiryDateRefreshToken(new Date(new Date().getTime() + 600000))
                .build();

        return tokenRepository.save(token);
    }

    @Override
    public String createRefreshToken() {
        return UUID.randomUUID().toString();
    }

    private Optional<Token> findByRefreshToken(String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken);
    }

    @Override
    public boolean existsByUserId(UUID uuid) {
        return tokenRepository.existsByUserId(uuid);
    }

    @Override
    public void deleteByUserId(UUID uuid) {
        tokenRepository.deleteByUserId(uuid);
    }

    @Override
    public void deleteByJwtToken(String jwtToken) {
        tokenRepository.deleteByJwtToken(jwtToken);
    }

    @Override
    public Token verifyExpirationRefreshToken(Token token) {
        if (token.getExpiryDateRefreshToken().before(new Date())) {
            tokenRepository.delete(token);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Refresh Token has expired. Please login again to get a new refresh token.");
        }
        return token;
    }

    @Override
    public JwtResponse refreshToken(RefreshTokenRequest request) {
        return findByRefreshToken(request.getRefreshToken())
                .map(this::verifyExpirationRefreshToken)
                .map(Token::getUser)
                .map(user -> {
                    tokenRepository.deleteByRefreshToken(request.getRefreshToken());
                    String jwtToken = jwtService.generateToken(user);
                    String refreshToken = createRefreshToken();

                    Token token = createToken(user.getEmail(), jwtToken, refreshToken);

                    return JwtResponse.builder()
                            .jwtToken(token.getJwtToken())
                            .refreshToken(token.getRefreshToken())
                            .email(user.getEmail())
                            .build();
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid refresh token"));
    }
}
