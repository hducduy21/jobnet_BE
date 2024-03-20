package com.jobnet.apigateway.services.impl;

import com.jobnet.apigateway.configs.security.CustomUserDetails;
import com.jobnet.apigateway.dtos.requests.AuthRequest;
import com.jobnet.apigateway.dtos.responses.AuthResponse;
import com.jobnet.apigateway.dtos.responses.UserResponse;
import com.jobnet.apigateway.services.IAuthService;
import com.jobnet.apigateway.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements IAuthService {

    private final ReactiveUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public Mono<AuthResponse> login(AuthRequest authRequest) {
        return userDetailsService.findByUsername(authRequest.getEmail())
            .cast(CustomUserDetails.class)
            .map(userDetails -> {
                if (!isUserValid(userDetails, authRequest))
                    throw new BadCredentialsException("Username or password does not match.");

                String accessToken = jwtUtils.generateToken(userDetails, TimeUnit.MINUTES.toMillis(10));
                String refreshToken = jwtUtils.generateToken(userDetails, TimeUnit.DAYS.toMillis(1));
                UserResponse user = UserResponse.builder()
                    .id(userDetails.getUser().getId())
                    .email(userDetails.getUser().getEmail())
                    .name(userDetails.getUser().getName())
                    .role(userDetails.getUser().getRole())
                    .build();
                log.info("Login user");
                return AuthResponse.builder()
                    .user(user)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
            });
    }

    @Override
    public Mono<AuthResponse> refresh(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer"))
            throw new BadCredentialsException("Invalid authorization header.");

        final String refreshToken = authHeader.substring(7);

        return Mono.just(refreshToken)
            .map(jwtUtils::extractUsername)
            .flatMap(userDetailsService::findByUsername)
            .cast(CustomUserDetails.class)
            .map(userDetails -> {
                if (!jwtUtils.isTokenValid(refreshToken, userDetails))
                    throw new BadCredentialsException("Token invalid.");

                String accessToken = jwtUtils.generateToken(userDetails, TimeUnit.MINUTES.toMillis(10));
                UserResponse user = UserResponse.builder()
                    .id(userDetails.getUser().getId())
                    .email(userDetails.getUser().getEmail())
                    .name(userDetails.getUser().getName())
                    .role(userDetails.getUser().getRole())
                    .build();
                log.info("Refresh token");
                return AuthResponse.builder()
                    .user(user)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
            });
    }

    private boolean isUserValid(UserDetails userDetails, AuthRequest authRequest) {
        return userDetails.getUsername().equals(authRequest.getEmail()) &&
            passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword());
    }
}
