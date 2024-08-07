package org.yosua.binfood.handler.oauth2;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public class OAuth2UserRegistrationEvent extends ApplicationEvent {
    public OAuth2UserRegistrationEvent(OAuth2AuthenticationToken token) {
        super(token);
    }
}
