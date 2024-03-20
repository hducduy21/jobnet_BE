package com.jobnet.resume.dtos.requests;

import com.jobnet.resume.validations.AccessPermission;
import com.jobnet.resume.validations.SupportPermission;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResumeRequest {

    @NotNull(message = "Access permission is required.")
    @AccessPermission
    private String accessPermission;

    @NotNull(message = "Support permission is required.")
    @SupportPermission
    private String supportPermission;

}
