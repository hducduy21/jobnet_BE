package com.jobnet.user.dtos.requests;

import com.jobnet.user.validations.Phone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecruiterInformation {

    @NotNull(message = "Name is required.")
    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotNull(message = "Phone is required.")
    @NotBlank(message = "Phone cannot be blank.")
    @Phone
    private String phone;

    @NotNull(message = "Nation is required.")
    @NotBlank(message = "Nation cannot be blank.")
    private String nation;
}
