package com.jobnet.clients.business;

import com.jobnet.common.dtos.LocationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BusinessResponse {
    private String id;
    private String name;
    private String type;
    private String country;
    private Integer employeeQuantity;
    private Integer foundedYear;
    private String description;
    private String emailDomain;
    private String phone;
    private String website;
    private List<LocationRequest> locations;
    private String profileImageId;
    private String backgroundImageId;
    private int totalFollowers;
    private Boolean isDeleted;
}