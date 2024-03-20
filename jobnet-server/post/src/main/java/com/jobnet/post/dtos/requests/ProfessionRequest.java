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
public class ProfessionRequest {

    @NotNull(message = "Name is required.")
    @NotBlank(message = "Name cannot be blank.")
    private String name;
    
    @NotNull(message = "Category ID is required.")
    private String categoryId;

    @NotNull(message = "English name is required.")
    private String englishName;
}