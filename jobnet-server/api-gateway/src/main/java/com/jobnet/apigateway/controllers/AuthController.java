package com.jobnet.apigateway.controllers;

import com.jobnet.apigateway.dtos.requests.AuthRequest;
import com.jobnet.apigateway.dtos.responses.AuthResponse;
import com.jobnet.apigateway.services.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final IAuthService authService;

    @PostMapping("login")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest)
            .map(response -> {
                log.info("Generate token successfully");
                return response;
            });
    }

    @PostMapping("refresh")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuthResponse> refresh(
        @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        return authService.refresh(authHeader)
            .map(response -> {
                log.info("Refresh token successfully");
                return response;
            });
    }
}
