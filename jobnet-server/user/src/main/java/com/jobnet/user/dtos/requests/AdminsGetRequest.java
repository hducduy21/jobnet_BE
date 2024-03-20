package com.jobnet.user.dtos.requests;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminsGetRequest {

    @Min(value = 1, message = "{validation.page.min}")
    private Integer page = 1;

    @Min(value = 1, message = "{validation.pageSize.min}")
    private Integer pageSize = 10;

    private List<String> sortBys = List.of("createdAt-desc");
}
