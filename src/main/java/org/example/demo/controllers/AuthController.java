package org.example.demo.controllers;

import io.minio.errors.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo.dto.request.*;
import org.example.demo.dto.response.JwtAuthenticationResponse;
import org.example.demo.dto.response.SignupResponse;
import org.example.demo.entity.RefreshToken;
import org.example.demo.exceptions.RefreshTokenIsNotDBException;
import org.example.demo.service.AuthorizationService;
import org.example.demo.service.JwtService;
import org.example.demo.service.RefreshTokenService;
import org.example.demo.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthorizationService authorizationService;
    private final RegistrationService registrationService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @Operation(summary = "Initial registration")
    @PostMapping("/signup/initiate")
    public ResponseEntity<SignupResponse> initialSignup(
            @RequestBody @Valid SignupRequest request) {
        SignupResponse response = registrationService.signup(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @Operation(summary = "Verify email and complete registration")
    @PostMapping("/signup/verify")
    public ResponseEntity<JwtAuthenticationResponse> verifyAndComplete(
            @RequestBody @Valid VerifySignupRequest request) {
        JwtAuthenticationResponse response = registrationService.verifyAndComplete(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @Operation(summary = "Resend verification code")
    @PostMapping("/signup/resend")
    public ResponseEntity<Map<String, String>> resendCode(
            @RequestBody @Valid ResendCodeRequest request) {
        registrationService.resendCode(request.getEmail());
        return ResponseEntity.ok(Map.of(
                "message", "New verification code sent",
                "email", request.getEmail()
        ));
    }

    @Operation(summary = "Authorization")
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signIn(
            @RequestBody @Valid SignInRequest request) {
        JwtAuthenticationResponse response = authorizationService.signIn(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(
            @RequestBody TokenRefreshRequest request) {

        System.out.println(request.getRefreshToken());
        return refreshTokenService.findByToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user);
                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getId());
                    return ResponseEntity.ok(new JwtAuthenticationResponse(accessToken, newRefreshToken.getToken()));
                })
                .orElseThrow(() -> new RefreshTokenIsNotDBException("Refresh token is not in database!"));
    }

}
