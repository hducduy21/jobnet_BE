package com.jobnet.business.dtos.requests;

import com.jobnet.business.validations.BusinessStatus;
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
public class StatusUpdateRequest {

    @NotNull(message = "Status is required.")
    @NotBlank(message = "Status cannot be blank.")
    @BusinessStatus
    private String status;
}
