package com.jobnet.post.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostGeneralInfoUpdateRequest {

    @NotNull(message = "Level ID is required.")
    @NotBlank(message = "Level cannot be blank.")
    private String levelId;

    @NotNull(message = "Profession ID is required.")
    @NotBlank(message = "Profession ID cannot be blank.")
    private String professionId;

    @NotNull(message = "Benefit IDs is required.")
    @NotEmpty(message = "Benefit IDs cannot be empty.")
    private List<String> benefitIds;

    @NotNull(message = "Working format is required.")
    @NotBlank(message = "Working format cannot be blank.")
    private String workingFormat;
}
