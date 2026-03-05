package org.example.demo.controllers;

import io.minio.errors.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo.dto.request.*;
import org.example.demo.dto.response.JwtAuthenticationResponse;
import org.example.demo.dto.response.SignupResponse;
import org.example.demo.service.AuthorizationService;
import org.example.demo.service.MinioService;
import org.example.demo.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthorizationService authorizationService;
    private final RegistrationService registrationService;
    private final MinioService minioService;

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

}
