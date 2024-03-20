package com.jobnet.user.controllers;

import com.jobnet.user.dtos.requests.VerificationRequest;
import com.jobnet.user.dtos.requests.JobSeekerRegisterRequest;
import com.jobnet.user.dtos.requests.RecruiterWithNewBusinessRegisterRequest;
import com.jobnet.user.dtos.requests.RecruiterWithSelectedBusinessRegisterRequest;
import com.jobnet.user.dtos.responses.UserResponse;
import com.jobnet.user.services.IRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final IRegistrationService registrationService;

    @PostMapping("jobSeeker")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerJobSeeker(@RequestBody @Valid JobSeekerRegisterRequest request) {
        return registrationService.registerJobSeeker(request);
    }

    @PostMapping("recruiter/newBusiness")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerRecruiterWithNewBusiness(
        @RequestBody @Valid RecruiterWithNewBusinessRegisterRequest request
    ) {
        return registrationService.registerRecruiterWithNewBusiness(request);
    }

    @PostMapping("recruiter/selectedBusiness")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerRecruiterWithSelectedBusiness(
        @RequestBody @Valid RecruiterWithSelectedBusinessRegisterRequest request
    ) {
        return registrationService.registerRecruiterWithSelectedBusiness(request);
    }

    @PostMapping("verify")
    @ResponseStatus(HttpStatus.OK)
    public String verifyUser(@RequestBody @Valid VerificationRequest request) {
        return registrationService.verifyUser(request);
    }
}
