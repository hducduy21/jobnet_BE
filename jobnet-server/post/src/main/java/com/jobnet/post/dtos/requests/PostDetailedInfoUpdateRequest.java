package com.jobnet.post.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDetailedInfoUpdateRequest {

    @NotNull(message = "Description is required.")
    @NotBlank(message = "Description cannot be blank.")
    private String description;

    @NotNull(message = "Other requirements is required.")
    @NotBlank(message = "Other requirements cannot be blank.")
    private String otherRequirements;
}
