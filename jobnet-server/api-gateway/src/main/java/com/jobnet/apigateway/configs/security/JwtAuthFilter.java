package com.jobnet.apigateway.configs.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobnet.apigateway.dtos.responses.ErrorResponse;
import com.jobnet.apigateway.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements WebFilter {

    private final ReactiveUserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        final String authHeader = exchange.getRequest()
            .getHeaders()
            .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer"))
            return chain.filter(exchange);

        final String jwtToken = authHeader.substring(7);

        return Mono.just(jwtToken)
            .map(jwtUtils::extractUsername)
            .flatMap(userDetailsService::findByUsername)
            .cast(CustomUserDetails.class)
            .flatMap(userDetails -> {
                if (!jwtUtils.isTokenValid(jwtToken, userDetails))
                    return Mono.error(new BadCredentialsException("Invalid token."));

                Authentication auth = new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
                );
                return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
            })
            .onErrorResume(throwable -> {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                ErrorResponse errorResponse = ErrorResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .message(throwable.getMessage())
                    .build();
                byte[] errorMessageBytes;
                try {
                    errorMessageBytes = objectMapper.writeValueAsBytes(errorResponse);
                } catch (JsonProcessingException e) {
                    errorMessageBytes = "{\"message\":\"Serialization error\"}".getBytes(StandardCharsets.UTF_8);
                }
                DataBuffer buffer = response.bufferFactory().wrap(errorMessageBytes);
                return response.writeWith(Mono.just(buffer));
            });
    }
}
