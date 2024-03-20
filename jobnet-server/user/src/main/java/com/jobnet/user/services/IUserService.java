package com.jobnet.user.services;

import com.jobnet.user.dtos.requests.VerificationRequest;
import com.jobnet.user.models.VerificationOTP;

public interface IUserService {

    VerificationOTP verifyUser(VerificationRequest request);
}
