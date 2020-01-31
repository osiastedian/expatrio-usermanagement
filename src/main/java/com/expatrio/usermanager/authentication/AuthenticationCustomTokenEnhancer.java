package com.expatrio.usermanager.authentication;

import com.expatrio.usermanager.user.domain.User;
import com.expatrio.usermanager.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationCustomTokenEnhancer implements TokenEnhancer {

    @Autowired
    UserService userService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = userService.loadUserByUsername(authentication.getName());
            Map<String, Object> additionalInfo = new HashMap<>();
            additionalInfo.put("id", user.getId());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(
                    additionalInfo);
            return accessToken;
    }
}
