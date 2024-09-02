package org.yosua.binfood.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.yosua.binfood.model.entity.User;

public interface JwtService {
    String generateToken(User user);
    String getEmail(String jwt);
    boolean isTokenValid(String token, UserDetails user);
}
