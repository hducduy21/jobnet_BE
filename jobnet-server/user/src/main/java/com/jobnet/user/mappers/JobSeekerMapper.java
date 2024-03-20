package com.jobnet.user.mappers;

import com.jobnet.user.dtos.requests.JobSeekerRegisterRequest;
import com.jobnet.user.dtos.responses.JobSeekerResponse;
import com.jobnet.user.models.JobSeeker;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class JobSeekerMapper {

    public Function<JobSeekerRegisterRequest, JobSeeker> convertRegisterRequestToJobSeeker =
        registerRequest ->
            JobSeeker.builder()
                .email(registerRequest.getEmail())
                .name(registerRequest.getName())
                .build();

    public Function<JobSeeker, JobSeekerResponse> convertToJobSeekerResponse =
        jobSeeker ->
            JobSeekerResponse.builder()
                .id(jobSeeker.getId())
                .email(jobSeeker.getEmail())
                .name(jobSeeker.getName())
                .role(jobSeeker.getRole())
                .gender(jobSeeker.getGender())
                .dateOfBirth(jobSeeker.getDateOfBirth())
                .phone(jobSeeker.getPhone())
                .address(jobSeeker.getAddress())
                .nation(jobSeeker.getNation())
                .salary(jobSeeker.getSalary())
                .workingFormat(jobSeeker.getWorkingFormat())
                .profession(jobSeeker.getProfession())
                .location(jobSeeker.getLocation())
                .profileImageId(jobSeeker.getProfileImageId())
                .verificationStatus(jobSeeker.getVerificationStatus())
                .jobSearchStatus(jobSeeker.getJobSearchStatus())
                .accountType(jobSeeker.getAccountType())
                .subscribesToJobOpportunities(jobSeeker.getSubscribesToJobOpportunities())
                .totalBusinessFollowed(jobSeeker.getTotalBusinessFollowed())
                .locked(jobSeeker.getLocked())
                .build();
}
