package org.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.entity.User;
import org.example.demo.entity.enums.Role;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("Google user data: {}", attributes);

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String googleId = (String) attributes.get("sub");

        User user = userService.findByEmail(email)
                .map(existingUser -> {
                    if (existingUser.getGoogleId() == null) {
                        existingUser.setGoogleId(googleId);
                        existingUser.setProvider("google");
                        return userService.save(existingUser);
                    }
                    return existingUser;
                })
                .orElseGet(() -> {
                    return userService.createOAuth2User(name, email, googleId);
                });

        Map<String, Object> enhancedAttributes = new HashMap<>(attributes);
        enhancedAttributes.put("userId", user.getId());
        enhancedAttributes.put("role", user.getRole().name());

        return new DefaultOAuth2User(
                user.getAuthorities(),
                enhancedAttributes,
                "email"
        );
    }
}