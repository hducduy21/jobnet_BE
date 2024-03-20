package com.jobnet.user.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VerificationRequest {

    @NotBlank(message = "{validation.userId.notBlank}")
    private String userId;

    @NotBlank(message = "{validation.otpToken.notBlank}")
    private String otpToken;
}
