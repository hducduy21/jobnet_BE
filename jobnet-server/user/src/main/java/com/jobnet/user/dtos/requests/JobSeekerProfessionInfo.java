package com.jobnet.user.dtos.requests;

import com.jobnet.common.dtos.LocationRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobSeekerProfessionInfo {

    @NotBlank(message = "{validation.salary.notBlank}")
    private String salary;

    @NotBlank(message = "{validation.workingFormat.notBlank}")
    private String workingFormat;

    @NotBlank(message = "{validation.profession.notBlank}")
    private String profession;

    @NotNull(message = "{validation.location.notNull}")
    @Valid
    private LocationRequest location;
}
