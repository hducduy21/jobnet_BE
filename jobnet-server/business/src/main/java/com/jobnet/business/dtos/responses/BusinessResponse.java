package com.jobnet.business.dtos.responses;

import com.jobnet.business.models.Business;
import com.jobnet.common.dtos.LocationRequest;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString(of = {"id", "name"})
public class BusinessResponse {
    private String id;
    private String name;
    private Business.EType type;
    private String country;
    private Integer employeeQuantity;
    private Integer foundedYear;
    private String description;
    private String email;
    private String emailDomain;
    private String phone;
    private String website;
    private List<String> locations;
    private String profileImageId;
    private String backgroundImageId;
    private List<String> followers;
    private int totalFollowers;
    private LocalDateTime createdAt;
    private Business.EStatus status;
    private Boolean isDeleted;
}
