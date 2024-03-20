package com.jobnet.user.services.impl;

import com.jobnet.common.exceptions.ResourceNotFoundException;
import com.jobnet.common.i18n.MessageUtil;
import com.jobnet.user.dtos.requests.VerificationRequest;
import com.jobnet.user.models.VerificationOTP;
import com.jobnet.user.models.User;
import com.jobnet.user.repositories.UserRepository;
import com.jobnet.user.services.IVerificationOTPService;
import com.jobnet.user.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final IVerificationOTPService confirmationOTPService;
    private final MessageUtil messageUtil;

    @Override
    public VerificationOTP verifyUser(VerificationRequest request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("error.notFound.user")));

        VerificationOTP verificationOTP =
            confirmationOTPService.updateVerificationOTPConfirmedAt(user, request.getOtpToken());

        user.setEnabled(true);
        userRepository.save(user);
        return verificationOTP;
    }
}
