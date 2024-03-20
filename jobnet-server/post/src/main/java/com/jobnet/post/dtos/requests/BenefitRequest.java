package com.jobnet.post.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BenefitRequest {
    @NotNull(message = "Name is required.")
    @NotBlank(message = "Name cannot be blank.")
    private String name;

//    @NotNull(message = "Description is required.")
//    @NotBlank(message = "Description cannot be blank.")
    private String description;
}
