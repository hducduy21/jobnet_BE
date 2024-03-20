package com.jobnet.user.models;

import com.jobnet.common.dtos.LocationRequest;
import com.jobnet.user.models.enums.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document("users")
@NoArgsConstructor
@Getter
@Setter
public class JobSeeker extends User {

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
    

    @Builder
    public JobSeeker(
            String id,
            String email,
            String password,
            String name,
            ERole role,
            Boolean locked,
            Boolean enabled,
            EGender gender,
            LocalDate dateOfBirth,
            String phone,
            String address,
            String nation,
            String salary,
            String workingFormat,
            String profession,
            LocationRequest location,
            String profileImageId,
            EVerificationStatus verificationStatus,
            EJobSearchStatus jobSearchStatus,
            Boolean subscribesToJobOpportunities,
            EAccountType accountType,
            List<String> businessFollowed,
            int totalBusinessFollowed
    ) {
        super(id, email, password, name, role, locked, enabled);
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
        this.nation = nation;
        this.salary = salary;
        this.workingFormat = workingFormat;
        this.profession = profession;
        this.location = location;
        this.profileImageId = profileImageId;
        this.verificationStatus = verificationStatus;
        this.jobSearchStatus = jobSearchStatus;
        this.subscribesToJobOpportunities = subscribesToJobOpportunities;
        this.accountType = accountType;
        this.businessFollowed = businessFollowed;
        this.totalBusinessFollowed = totalBusinessFollowed;
    }
}
