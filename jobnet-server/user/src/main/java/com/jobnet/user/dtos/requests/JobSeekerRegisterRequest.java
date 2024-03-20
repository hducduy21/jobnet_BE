package com.jobnet.user.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobSeekerRegisterRequest {

    @NotBlank(message = "{validation.email.notBlank}")
    @Email(message = "{validation.email.notValid}")
    private String email;

    @NotBlank(message = "{validation.password.notBlank}")
    private String password;

    @NotBlank(message = "{validation.name.notBlank}")
    private String name;
}

