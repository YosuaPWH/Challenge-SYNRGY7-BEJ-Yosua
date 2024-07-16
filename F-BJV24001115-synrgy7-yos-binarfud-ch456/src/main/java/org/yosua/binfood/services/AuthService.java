package org.yosua.binfood.services;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.yosua.binfood.model.dto.request.LoginUserRequest;
import org.yosua.binfood.model.dto.request.RegisterUserRequest;
import org.yosua.binfood.model.dto.response.JwtResponse;
import org.yosua.binfood.model.dto.response.UserResponse;

public interface AuthService {
    UserResponse register(RegisterUserRequest request);
    JwtResponse login(LoginUserRequest request);
    UserResponse registerOAuth2User(DefaultOAuth2User oAuth2User);
    JwtResponse loginOAuth2User(DefaultOAuth2User oAuth2User);
}
