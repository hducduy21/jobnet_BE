package com.jobnet.user.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecruiterBusinessRequest {
    
    @NotNull(message = "Business ID is required.")
    @NotBlank(message = "Business ID cannot be blank.")
    private String businessId;
}
