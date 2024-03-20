package com.jobnet.business.dtos.requests;

import com.jobnet.common.dtos.LocationRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class BusinessContactInfo {

    @NotNull(message = "Phone is required.")
    @NotBlank(message = "Phone cannot be blank.")
    private String phone;

    @NotNull(message = "Website is required.")
    @NotBlank(message = "Website cannot be blank.")
    private String website;

    @NotNull(message = "Locations is required.")
    private List<String> locations;
}
