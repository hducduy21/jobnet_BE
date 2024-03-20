package com.jobnet.business.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BusinessIntroductionInfo {

    @NotNull(message = "Description is required.")
    @NotBlank(message = "Description cannot be blank.")
    private String description;
}
