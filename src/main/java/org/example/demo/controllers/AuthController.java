package org.example.demo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo.dto.request.EmailVerificationRequest;
import org.example.demo.dto.request.SignInRequest;
import org.example.demo.dto.request.SignupRequest;
import org.example.demo.dto.request.VerifySignupRequest;
import org.example.demo.dto.response.JwtAuthenticationResponse;
import org.example.demo.dto.response.SignupResponse;
import org.example.demo.service.AuthorizationService;
import org.example.demo.service.GoogleOAuth2UserService;
import org.example.demo.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthorizationService authorizationService;
    private final RegistrationService registrationService;
    private final GoogleOAuth2UserService googleOAuth2UserService;

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
            @RequestBody @Valid EmailVerificationRequest request) {
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

    @Operation(summary = "Google OAuth 2")
    @PostMapping("/google")
    public ResponseEntity<Void> googleLogin() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/oauth2/authorization/google"))
                .build();
    }

    @Operation(summary = "Google OAuth2 callback")
    @GetMapping("/google/callback")
    public ResponseEntity<JwtAuthenticationResponse> googleCallback(@RequestParam String token) {
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @Operation(summary = "Get current Google user")
    @GetMapping("/google/user")
    public ResponseEntity<?> getGoogleUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Not authenticated"));
        }

        return ResponseEntity.ok(Map.of(
                "email", principal.getAttributes().get("email"),
                "name", principal.getAttributes().get("name"),
                "googleId", principal.getAttributes().get("sub")
        ));
    }

}
