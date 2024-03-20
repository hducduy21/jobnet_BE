package com.jobnet.user.dtos.responses;

import com.jobnet.user.models.enums.EGender;
import com.jobnet.user.models.enums.ERole;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString(of = {"id", "name"})
public class AdminResponse{
    private String id;
    private String email;
    private String name;
    private ERole role;
    private EGender gender;
    private String phone;
    private LocalDate dateOfBirth;
}
