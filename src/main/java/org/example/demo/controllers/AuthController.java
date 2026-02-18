package org.example.demo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo.dto.request.SignInRequest;
import org.example.demo.dto.request.SignupRequest;
import org.example.demo.dto.response.JwtAuthenticationResponse;
import org.example.demo.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Registration")
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signup(@RequestBody @Valid SignupRequest request) {
        return authenticationService.signup(request);
    }

    @Operation(summary = "Authorization")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
