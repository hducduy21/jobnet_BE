package com.jobnet.clients.business;

public record BusinessRegisterRequest(
    String name,
    String emailDomain
) {
}
