package com.jobnet.clients.user.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JobSeekerResponse {
    private String id;
    private String name;
    private String gender;
    private LocalDate dateOfBirth;
    private String profileImageId;

}
