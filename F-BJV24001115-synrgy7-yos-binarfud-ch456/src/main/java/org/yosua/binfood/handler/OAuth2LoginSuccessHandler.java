package org.yosua.binfood.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.yosua.binfood.repositories.UserRepository;
import org.yosua.binfood.services.AuthService;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final AuthService authService;

    @Autowired
    public OAuth2LoginSuccessHandler(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("principal: " + authentication.getPrincipal());
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        System.out.println(email);

        if (userRepository.existsByEmail(email)) {
            // Existing User
            authService.loginOAuth2User(oAuth2User);
        } else {
            // New User
            authService.registerOAuth2User(oAuth2User);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private void registerOAuth2User(DefaultOAuth2User oAuth2User) {
        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");


    }
}
