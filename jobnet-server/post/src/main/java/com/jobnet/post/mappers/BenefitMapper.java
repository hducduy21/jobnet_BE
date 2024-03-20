package com.jobnet.post.mappers;

import com.jobnet.post.dtos.requests.BenefitRequest;
import com.jobnet.post.dtos.responses.BenefitResponse;
import com.jobnet.post.models.Benefit;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BenefitMapper {

    public Function<BenefitRequest, Benefit> convertToBenefit =
        benefitRequest ->
            Benefit.builder()
                .name(benefitRequest.getName())
                .description(benefitRequest.getDescription())
                .build();

    public Function<Benefit, BenefitResponse> convertToBenefitResponse =
        benefit ->
            BenefitResponse.builder()
                .id(benefit.getId())
                .name(benefit.getName())
                .description(benefit.getDescription())
                .build();
}
