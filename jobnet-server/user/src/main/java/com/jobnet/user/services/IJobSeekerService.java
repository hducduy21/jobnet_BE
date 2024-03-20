package com.jobnet.user.services;

import com.jobnet.common.utils.pagination.PaginationResponse;
import com.jobnet.user.dtos.requests.*;
import com.jobnet.user.dtos.responses.JobSeekerResponse;
import com.jobnet.user.models.VerificationOTP;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IJobSeekerService {

    PaginationResponse<List<JobSeekerResponse>> getJobSeekers(JobSeekersGetRequest request);

    JobSeekerResponse getJobSeekerById(String id);

    VerificationOTP createJobSeeker(JobSeekerRegisterRequest registerRequest);

    JobSeekerResponse updateJobSeekerPersonalInfo(String id, JobSeekerPersonalInfo personalInfo);

    JobSeekerResponse updateJobSeekerProfessionInfo(String id, JobSeekerProfessionInfo professionInfo);
    
    JobSeekerResponse updateJobSeekerBusinessFollowed(String id, JobSeekerBusinessFollowedInfo businessId);
    
    void deleteJobSeekerById(String id, boolean locked);

    JobSeekerResponse uploadJobSeekerProfileImage(String id, MultipartFile file);

    byte[] getJobSeekerProfileImage(String id);

}
