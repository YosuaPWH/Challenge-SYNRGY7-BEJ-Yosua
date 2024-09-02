package org.yosua.binfood.handler.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.yosua.binfood.repositories.UserRepository;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public OAuth2LoginSuccessHandler(UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("principal: " + authentication.getPrincipal());
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) oAuth2AuthenticationToken.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        System.out.println(email);

        if (userRepository.existsByEmail(email)) {
            // Existing User
            applicationEventPublisher.publishEvent(new OAuth2UserRegistrationEvent(oAuth2AuthenticationToken));
        } else {
            // New User
            applicationEventPublisher.publishEvent(new OAuth2UserLoginEvent(oAuth2AuthenticationToken));
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
