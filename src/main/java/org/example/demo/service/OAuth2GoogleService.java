package org.example.demo.service;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.entity.User;
import org.example.demo.entity.enums.Provider;
import org.example.demo.entity.enums.Role;
import org.example.demo.handler.OAuth2AuthenticationSuccessHandler;
import org.example.demo.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2GoogleService extends DefaultOAuth2UserService {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("Login user by google with email: {}", attributes.get("email"));
        if (userService.findByEmail(attributes.get("email").toString()).isEmpty()) {
            String username;
            if (userService.getByUsername(attributes.get("name").toString()) == null) {
                username = attributes.get("name").toString();
            } else {
                username = (attributes.get("name").toString() + userService.getLastId().toString());
            }
            User user = User.builder()
                    .username(username)
                    .email(attributes.get("email").toString())
                    .role(Role.ROLE_USER)
                    .enabled(true)
                    .googleId(attributes.get("sub").toString())
                    .provider(Provider.GOOGLE)
                    .build();
            userService.save(user);
            log.info("User with email: {} saved from DB", attributes.get("email"));
        } else {
            if (userService.getProviderByEmail(attributes.get("email").toString())
                    .orElse(null) == Provider.EMAIL) {
                userService.updateProvider(attributes.get("email").toString(), Provider.EMAIL_AND_GOOGLE);
            log.info("Updated provider users: {}", attributes.get("email"));
            }
        }

        Map<String, Object> customAttributes = new HashMap<>(attributes);
        customAttributes.put("role", userService.getRoleByEmail(attributes.get("email").toString())
                .orElse(Role.ROLE_USER));

        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                customAttributes,
                "email"
        );
    }

}
