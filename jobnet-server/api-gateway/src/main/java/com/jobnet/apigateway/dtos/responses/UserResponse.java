package com.jobnet.apigateway.dtos.responses;

import com.jobnet.apigateway.models.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponse {
    private String id;
    private String email;
    private String name;
    private ERole role;
}
