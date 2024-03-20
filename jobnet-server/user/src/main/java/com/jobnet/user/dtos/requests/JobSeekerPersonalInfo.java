package com.jobnet.user.dtos.requests;

import com.jobnet.user.validations.Phone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobSeekerPersonalInfo {

    @NotBlank(message = "{validation.name.notBlank}")
    private String name;

    @NotBlank(message = "{validation.email.notBlank}")
    @Email(message = "{validation.email.notValid}")
    private String email;

    @NotBlank(message = "{validation.phone.notBlank}")
    @Phone(message = "{validation.phone.notValid}")
    private String phone;

    @NotBlank(message = "{validation.nation.notBlank}")
    private String nation;
}
