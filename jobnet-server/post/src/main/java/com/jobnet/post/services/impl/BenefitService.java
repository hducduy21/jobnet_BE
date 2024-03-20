package com.jobnet.post.services.impl;

import com.jobnet.common.exceptions.DataIntegrityViolationException;
import com.jobnet.common.exceptions.ResourceNotFoundException;
import com.jobnet.post.dtos.requests.BenefitRequest;
import com.jobnet.post.dtos.responses.BenefitResponse;
import com.jobnet.post.mappers.BenefitMapper;
import com.jobnet.post.models.Benefit;
import com.jobnet.post.repositories.BenefitRepository;
import com.jobnet.post.services.IBenefitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BenefitService implements IBenefitService {
    private final BenefitRepository benefitRepository;
    private final BenefitMapper benefitMapper;

    @Override
    public List<BenefitResponse> getBenefits(String search) {
        List<Benefit> benefits = StringUtils.isBlank(search)
            ? benefitRepository.findAll()
            : benefitRepository.findByNameContainsIgnoreCase(search);

        List<BenefitResponse> responses = benefits.stream()
            .map(this::getBenefitResponse)
            .toList();
        log.info("Get benefits: {}", responses);
        return responses;
    }

    @Override
    public BenefitResponse getBenefitById(String id) {
        Benefit benefit =  benefitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Benefit not found"));
        BenefitResponse response = this.getBenefitResponse(benefit);
        log.info("Get benefit by id - id={}: {}", id, response);
        return response;
    }

    @Override
    public BenefitResponse createBenefit(BenefitRequest benefitRequest) {
        if (benefitRepository.existsByName(benefitRequest.getName()))
            throw new DataIntegrityViolationException("Name is already in use.");

        // Check duplicates by context

        Benefit _benefit = benefitMapper.convertToBenefit.apply(benefitRequest);
        Benefit benefit = benefitRepository.save(_benefit);
        BenefitResponse response = this.getBenefitResponse(benefit);
        log.info("Create benefit: {}", response);
        return response;
    }

    @Override
    public BenefitResponse updateBenefit(String id,BenefitRequest benefitRequest) {
        Benefit benefit =  benefitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Benefit not found"));

        if (benefitRepository.existsByIdNotAndName(id, benefitRequest.getName()))
            throw new RuntimeException("Name is already in use.");

        // Check duplicates by context

        benefit.setName(benefitRequest.getName());
        benefit.setDescription(benefitRequest.getDescription());
        benefitRepository.save(benefit);
        BenefitResponse response = this.getBenefitResponse(benefit);
        log.info("Update benefit: {}", response);
        return response;
    }

    @Override
    public void deleteBenefitById(String id) {
        if (!benefitRepository.existsById(id))
            throw new ResourceNotFoundException("Benefit not found.");
        benefitRepository.deleteById(id);
        log.info("Delete benefit by id - id={}", id);
    }

    @Override
    public List<Benefit> findAllById(Iterable<String> ids) {
        List<Benefit> benefits = benefitRepository.findAllById(ids);
        log.info("Get benefits by ids - ids={}", ids);
        return benefits;
    }

    private BenefitResponse getBenefitResponse(Benefit benefit){
        return benefitMapper.convertToBenefitResponse.apply(benefit);
    }
}
