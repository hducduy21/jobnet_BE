package com.jobnet.apigateway.configs.security;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthGatewayGlobalFilter implements GlobalFilter {

    private final CustomUserDetailsService userDetailsService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
            .flatMap(securityContext -> {
                Authentication auth = securityContext.getAuthentication();

                if (auth == null)
                    return chain.filter(exchange);

                String username = (String) auth.getPrincipal();
                return userDetailsService.findByUsername(username)
                    .cast(CustomUserDetails.class)
                    .flatMap(userDetails -> {
                        ServerHttpRequest request = exchange.getRequest().mutate()
                            .header("userId", userDetails.getUser().getId())
                            .build();
                        return chain.filter(
                            exchange.mutate()
                                .request(request)
                                .build()
                        );
                    });
            })
            .switchIfEmpty(chain.filter(exchange));
    }
}
