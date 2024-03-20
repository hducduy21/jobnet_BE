package com.jobnet.user.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jobnet.user.models.enums.ERole;
import com.jobnet.user.validations.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AdminRequest {

    @NotBlank(message = "{validation.email.notBlank}")
    @Email(message = "{validation.email.notValid}")
    private String email;

    @NotBlank(message = "{validation.password.notBlank}")
    private String password;

    @NotBlank(message = "{validation.name.notBlank}")
    private String name;

    @NotNull(message = "{validation.role.notNull}")
    private ERole role;

    @Gender(message = "{validation.gender.notValid}")
    private String gender;

    @NotBlank(message = "{validation.phone.notBlank}")
    private String phone;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

}

