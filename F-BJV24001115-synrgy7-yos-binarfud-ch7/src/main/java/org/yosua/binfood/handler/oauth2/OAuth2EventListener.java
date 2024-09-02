package org.yosua.binfood.handler.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.yosua.binfood.services.AuthService;

@Component
public class OAuth2EventListener {
    private final AuthService authService;

    @Autowired
    public OAuth2EventListener(AuthService authService) {
        this.authService = authService;
    }

    @EventListener
    public void handleOAuth2UserRegistrationEvent(OAuth2UserRegistrationEvent event) {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) event.getSource();
        authService.registerOAuth2User(token.getPrincipal());
    }

    @EventListener
    public void handleOAuth2UserLoginEvent(OAuth2UserLoginEvent event) {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) event.getSource();
        authService.loginOAuth2User(token.getPrincipal());
    }
}
