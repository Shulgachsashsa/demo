package org.example.demo.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.dto.response.JwtAuthenticationResponse;
import org.example.demo.entity.User;
import org.example.demo.repository.UserRepository;
import org.example.demo.service.RefreshTokenService;
import org.springframework.security.core.Authentication;
import org.example.demo.service.JwtService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        sendJsonResponse(response,
                JwtAuthenticationResponse.builder()
                        .accessToken(jwtService.generateToken(
                                userRepository.findByEmail(oAuth2User.getAttribute("email"))
                                        .orElse(null)))
                        .refreshToken(String.valueOf(refreshTokenService.createRefreshToken(
                                userRepository.getIdByEmail(oAuth2User.getAttribute("email"))).getToken()))
                        .build());
    }

    private void sendJsonResponse(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        this.setAlwaysUseDefaultTargetUrl(false);
        objectMapper.writeValue(response.getWriter(), data);
    }

}
