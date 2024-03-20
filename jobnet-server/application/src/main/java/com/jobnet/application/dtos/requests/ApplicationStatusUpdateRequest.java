package com.jobnet.application.dtos.requests;

import com.jobnet.application.validations.ApplicationStatus;
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
public class ApplicationStatusUpdateRequest {

    @NotNull(message = "Application status is required.")
    @NotBlank(message = "Application status cannot be blank.")
    @ApplicationStatus
    private String applicationStatus;
}
