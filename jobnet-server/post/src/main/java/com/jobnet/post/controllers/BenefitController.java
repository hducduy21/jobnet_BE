package com.jobnet.post.controllers;

import com.jobnet.post.dtos.requests.BenefitRequest;
import com.jobnet.post.dtos.responses.BenefitResponse;
import com.jobnet.post.services.IBenefitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/benefits")
@RequiredArgsConstructor
@Slf4j
public class BenefitController {
    private final IBenefitService benefitService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<BenefitResponse> getBenefits(@RequestParam(defaultValue = "") String search){
        List<BenefitResponse> benefitResponses = benefitService.getBenefits(search);
        log.info("Get benefits successfully");
        return benefitResponses;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public BenefitResponse getBenefitById(@PathVariable("id") String id) {
        BenefitResponse benefitResponse = benefitService.getBenefitById(id);
        log.info("Get benefit by id successfully");
        return benefitResponse;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BenefitResponse createBenefit(@RequestBody @Valid BenefitRequest benefitRequest) {
        BenefitResponse benefitResponse = benefitService.createBenefit(benefitRequest);
        log.info("Create benefit successfully");
        return benefitResponse;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public BenefitResponse updateBenefit(
        @PathVariable String id,
        @RequestBody @Valid BenefitRequest benefitRequest
    ) {
        BenefitResponse benefitResponse = benefitService.updateBenefit(id,benefitRequest);
        log.info("Update benefit successfully");
        return benefitResponse;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBenefit(@PathVariable("id") String id) {
        benefitService.deleteBenefitById(id);
        log.info("Delete benefit successfully");
    }
}
