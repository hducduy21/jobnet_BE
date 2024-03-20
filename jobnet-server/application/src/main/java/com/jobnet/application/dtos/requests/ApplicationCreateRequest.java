package com.jobnet.application.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class ApplicationCreateRequest {

    @NotNull(message = "Post ID is required.")
    @NotBlank(message = "Post ID cannot be blank.")
    private String postId;

    @NotNull(message = "Resume ID is required.")
    @NotBlank(message = "Resume ID cannot be blank.")
    private String resumeId;
}
