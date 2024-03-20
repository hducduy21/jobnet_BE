package com.jobnet.user.services.impl;

import com.jobnet.common.dtos.EmailVerification;
import com.jobnet.user.dtos.requests.VerificationRequest;
import com.jobnet.user.dtos.requests.JobSeekerRegisterRequest;
import com.jobnet.user.dtos.requests.RecruiterWithNewBusinessRegisterRequest;
import com.jobnet.user.dtos.requests.RecruiterWithSelectedBusinessRegisterRequest;
import com.jobnet.user.dtos.responses.UserResponse;
import com.jobnet.user.models.VerificationOTP;
import com.jobnet.user.models.User;
import com.jobnet.user.services.*;
import com.jobnet.common.i18n.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService implements IRegistrationService {

    private final IJobSeekerService jobSeekerService;
    private final IRecruiterService recruiterService;
    private final IUserService userService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final MessageUtil messageUtil;

    @Override
    public UserResponse registerJobSeeker(JobSeekerRegisterRequest request) {
        VerificationOTP verificationOTP = jobSeekerService.createJobSeeker(request);
        EmailVerification emailVerification = new EmailVerification(
            request.getEmail(),
            request.getName(),
            verificationOTP.getToken()
        );
        kafkaTemplate.send("user-registration", emailVerification);

        log.info(messageUtil.getMessage("success.registration.jobSeeker", request));
        return this.getUserResponse(verificationOTP.getUser());
    }

    @Override
    public UserResponse registerRecruiterWithNewBusiness(RecruiterWithNewBusinessRegisterRequest request) {
        VerificationOTP verificationOTP = recruiterService.createRecruiterWithNewBusiness(request);
        EmailVerification emailVerification = new EmailVerification(
            request.getEmail(),
            request.getName(),
            verificationOTP.getToken()
        );
        kafkaTemplate.send("user-registration", emailVerification);

        log.info(messageUtil.getMessage("success.registration.recruiter", request));
        return this.getUserResponse(verificationOTP.getUser());
    }

    @Override
    public UserResponse registerRecruiterWithSelectedBusiness(RecruiterWithSelectedBusinessRegisterRequest request) {
        VerificationOTP verificationOTP = recruiterService.createRecruiterWithSelectedBusiness(request);
        EmailVerification emailVerification = new EmailVerification(
            request.getEmail(),
            request.getName(),
            verificationOTP.getToken()
        );
        kafkaTemplate.send("user-registration", emailVerification);

        log.info(messageUtil.getMessage("success.registration.recruiter", request));
        return this.getUserResponse(verificationOTP.getUser());
    }

    @Override
    public String verifyUser(VerificationRequest request) {
        VerificationOTP verificationOTP = userService.verifyUser(request);

        String message = messageUtil.getMessage("success.registration.verify", verificationOTP.getUser().getId());
        log.info(message);
        return message;
    }

    private UserResponse getUserResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .role(user.getRole())
            .build();
    }
}
