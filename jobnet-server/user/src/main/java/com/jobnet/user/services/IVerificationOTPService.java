package com.jobnet.user.services;

import com.jobnet.user.models.VerificationOTP;
import com.jobnet.user.models.User;

public interface IVerificationOTPService {

    VerificationOTP createVerificationOTP(User user);

    VerificationOTP updateVerificationOTPConfirmedAt(User user, String token);
}
