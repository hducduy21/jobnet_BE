package com.jobnet.apigateway.services;

import com.jobnet.apigateway.dtos.requests.AuthRequest;
import com.jobnet.apigateway.dtos.responses.AuthResponse;
import com.netflix.appinfo.ApplicationInfoManager;
import reactor.core.publisher.Mono;

public interface IAuthService {

    Mono<AuthResponse> login(AuthRequest authRequest);

    Mono<AuthResponse> refresh(String authHeader);
}
