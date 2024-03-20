package com.jobnet.user.services;

import com.jobnet.user.dtos.requests.VerificationRequest;
import com.jobnet.user.dtos.requests.JobSeekerRegisterRequest;
import com.jobnet.user.dtos.requests.RecruiterWithNewBusinessRegisterRequest;
import com.jobnet.user.dtos.requests.RecruiterWithSelectedBusinessRegisterRequest;
import com.jobnet.user.dtos.responses.UserResponse;

public interface IRegistrationService {

    UserResponse registerJobSeeker(JobSeekerRegisterRequest request);

    UserResponse registerRecruiterWithNewBusiness(RecruiterWithNewBusinessRegisterRequest request);

    UserResponse registerRecruiterWithSelectedBusiness(RecruiterWithSelectedBusinessRegisterRequest request);

    String verifyUser(VerificationRequest request);
}
