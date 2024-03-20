package com.jobnet.resume.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EvaluationRequest {

    @NotNull(message = "Comment is required.")
    @NotBlank(message = "Comment cannot be blank.")
    private String comment;
}
