package com.jobnet.clients.post;

import com.jobnet.clients.business.BusinessResponse;
import com.jobnet.clients.user.dtos.responses.RawRecruiterResponse;
import com.jobnet.common.dtos.LocationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostResponse {
    private String id;
    private String title;
    private Profession profession;
    private String minSalaryString;
    private BigDecimal minSalary;
    private String maxSalaryString;
    private BigDecimal maxSalary;
    private List<Level> levels;
    private List<LocationRequest> locations;
    private String workingFormat;
    private List<Benefit> benefits;
    private String description;
    private String yearsOfExperience;
    private List<String> degrees;
    private String otherRequirements;
    private String internalContact;
    private String requisitionNumber;
    private String applicationDeadline;
    private String jdId;
    private RawRecruiterResponse recruiter;
    private BusinessResponse business;
    private String activeStatus;
    private Integer totalViews;
    private String createdAt;
}
