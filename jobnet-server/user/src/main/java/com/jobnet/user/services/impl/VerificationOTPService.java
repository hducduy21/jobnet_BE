package com.jobnet.user.services.impl;

import com.jobnet.common.exceptions.DataIntegrityViolationException;
import com.jobnet.common.exceptions.ResourceNotFoundException;
import com.jobnet.common.i18n.MessageUtil;
import com.jobnet.user.models.VerificationOTP;
import com.jobnet.user.models.User;
import com.jobnet.user.repositories.VerificationOTPRepository;
import com.jobnet.user.services.IVerificationOTPService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationOTPService implements IVerificationOTPService {

    private static final String OTP_CHARS = "0123456789";
    private static final int OTP_LENGTH = 6;

    private final VerificationOTPRepository verificationOTPRepository;
    private final MessageUtil messageUtil;

    @Override
    public VerificationOTP createVerificationOTP(User user) {
        VerificationOTP verificationOTP = verificationOTPRepository.save(
            VerificationOTP.builder()
                .token(this.generateOTP())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build()
        );
        log.info(messageUtil.getMessage("success.verification.create", verificationOTP));
        return verificationOTP;
    }

    @Override
    public VerificationOTP updateVerificationOTPConfirmedAt(User user, String token) {
        VerificationOTP verificationOTP = verificationOTPRepository.findByUserAndToken(user, token)
            .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("error.verification.notMatch")));

        if (verificationOTP.getConfirmedAt() != null)
            throw new DataIntegrityViolationException(messageUtil.getMessage("error.verification.confirmed"));
        if (verificationOTP.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new DataIntegrityViolationException(messageUtil.getMessage("error.verification.expired"));

        verificationOTP.setConfirmedAt(LocalDateTime.now());
        log.info(messageUtil.getMessage("success.verification.confirmed"));
        return verificationOTPRepository.save(verificationOTP);
    }

    private String generateOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = random.nextInt(OTP_CHARS.length());
            otp.append(OTP_CHARS.charAt(index));
        }

        return otp.toString();
    }
}
