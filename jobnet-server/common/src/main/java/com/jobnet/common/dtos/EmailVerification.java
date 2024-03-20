package com.jobnet.common.dtos;

public record EmailVerification(
    String recipient,
    String recipientName,
    String otpToken
){ }
