package com.jobnet.post.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LevelRequest {
    @NotNull(message = "Name is required.")
    @NotBlank(message = "Name cannot be blank.")
    private String name;
}
