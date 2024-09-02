package org.yosua.binfood.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yosua.binfood.model.entity.Token;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByRefreshToken(String token);
    Optional<Token> findByJwtToken(String jwtToken);
    boolean existsByUserId(UUID uuid);
    void deleteByUserId(UUID uuid);
    void deleteByJwtToken(String jwtToken);
    void deleteByRefreshToken(String refreshToken);
}
