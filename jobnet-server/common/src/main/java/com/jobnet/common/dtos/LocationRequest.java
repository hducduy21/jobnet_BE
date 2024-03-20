package com.jobnet.common.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LocationRequest {

//    @NotNull(message = "{validation.location.provinceCode.notNull}")
    private Integer provinceCode;

    @NotBlank(message = "{validation.location.provinceName.notBlank}")
    private String provinceName;

    @NotBlank(message = "{validation.location.specificAddress.notBlank}")
    private String specificAddress;
}
