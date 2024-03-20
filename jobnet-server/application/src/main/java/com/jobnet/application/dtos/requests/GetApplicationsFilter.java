package com.jobnet.application.dtos.requests;

import com.jobnet.application.models.Application;
import com.jobnet.common.utils.pagination.PaginationFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class GetApplicationsFilter extends PaginationFilter {

    private String jobSeekerId;
    private String recruiterId;
    private Application.EApplicationStatus applicationStatus;
    private List<Application.EApplicationStatus> applicationStatuses;
    private LocalDate fromDate;
    private LocalDate toDate;
}
