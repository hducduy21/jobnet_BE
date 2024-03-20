package com.jobnet.apigateway.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthResponse {

    private UserResponse user;
    private String accessToken;
    private String refreshToken;
    
}
