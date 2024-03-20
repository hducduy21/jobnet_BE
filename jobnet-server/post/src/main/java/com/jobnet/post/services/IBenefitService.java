package com.jobnet.post.services;

import com.jobnet.post.dtos.requests.BenefitRequest;
import com.jobnet.post.dtos.responses.BenefitResponse;
import com.jobnet.post.models.Benefit;

import java.util.List;

public interface IBenefitService {
    List<BenefitResponse> getBenefits(String search);

    BenefitResponse getBenefitById(String id);

    BenefitResponse createBenefit(BenefitRequest benefitRequest);

    BenefitResponse updateBenefit(String id,BenefitRequest benefitRequest);

    void deleteBenefitById(String id);

    List<Benefit> findAllById(Iterable<String> ids);
}
