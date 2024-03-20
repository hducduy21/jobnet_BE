package com.jobnet.user.dtos.responses;

import com.jobnet.common.dtos.LocationRequest;
import com.jobnet.user.models.enums.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString(of = {"id", "name"})
public class JobSeekerResponse {
    private String id;
    private String email;
    private String name;
    private ERole role;
    private EGender gender;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;
    private String nation;
    private String salary;
    private String workingFormat;
    private String profession;
    private LocationRequest location;
    private String profileImageId;
    private EVerificationStatus verificationStatus;
    private EJobSearchStatus jobSearchStatus;
    private Boolean subscribesToJobOpportunities;
    private EAccountType accountType;
    private List<String> businessFollowed;
    private int totalBusinessFollowed;
    private Boolean locked;
}
