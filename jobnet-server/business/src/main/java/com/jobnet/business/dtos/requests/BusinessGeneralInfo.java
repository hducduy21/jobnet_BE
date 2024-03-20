package com.jobnet.business.dtos.requests;

import com.jobnet.business.validations.BusinessType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class BusinessGeneralInfo {

    @NotNull(message = "Name is required.")
    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotNull(message = "Type is required.")
    @BusinessType
    private String type;

    @NotNull(message = "Country is required.")
    @NotBlank(message = "Country cannot be blank.")
    private String country;

    @NotNull(message = "Employee quantity is required.")
    private Integer employeeQuantity;

    @NotNull(message = "Founded year is required.")
    private Integer foundedYear;
}
