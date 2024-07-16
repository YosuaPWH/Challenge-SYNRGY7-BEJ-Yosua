package org.yosua.binfood.services;

import org.yosua.binfood.model.dto.request.RefreshTokenRequest;
import org.yosua.binfood.model.dto.response.JwtResponse;
import org.yosua.binfood.model.entity.Token;

import java.util.UUID;

public interface TokenService {
    Token createToken(String email, String jwtToken, String refreshToken);
    boolean existsByUserId(UUID uuid);
    void deleteByUserId(UUID uuid);
    Token verifyExpirationRefreshToken(Token token);
    JwtResponse refreshToken(RefreshTokenRequest request);
    String createRefreshToken();
}
