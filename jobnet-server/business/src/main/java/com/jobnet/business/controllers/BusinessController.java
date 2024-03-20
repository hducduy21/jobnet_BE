package com.jobnet.business.controllers;

import com.jobnet.business.dtos.requests.*;
import com.jobnet.business.dtos.responses.BusinessResponse;
import com.jobnet.business.services.IBusinessService;
import com.jobnet.clients.business.BusinessRegisterRequest;
import com.jobnet.common.utils.pagination.PaginationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/businesses")
@RequiredArgsConstructor
@Slf4j
public class BusinessController {

    private final IBusinessService businessService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponse<List<BusinessResponse>> getBusinesses(GetBusinessesFilter filter) {
        PaginationResponse<List<BusinessResponse>> response = businessService.getBusinesses(filter);
        log.info("Get business successfully");
        return response;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public BusinessResponse getBusinessById(@PathVariable String id) {
        BusinessResponse response = businessService.getBusinessById(id);
        log.info("Get business successfully");
        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BusinessResponse createBusiness(@RequestBody BusinessRegisterRequest request) {
        BusinessResponse response = businessService.createBusiness(request);
        log.info("Create business by auth successfully");
        return response;
    }

    @PutMapping("{id}/generalInfo")
    @ResponseStatus(HttpStatus.OK)
    public BusinessResponse updateBusinessGeneralInfo(
        @PathVariable String id,
        @RequestBody @Valid BusinessGeneralInfo generalInfo
    ) {
        BusinessResponse response = businessService.updateBusinessGeneralInfo(id, generalInfo);
        log.info("Update business by auth successfully");
        return response;
    }

    @PutMapping("{id}/introductionInfo")
    @ResponseStatus(HttpStatus.OK)
    public BusinessResponse updateBusinessIntroductionInfo(
        @PathVariable String id,
        @RequestBody @Valid BusinessIntroductionInfo introductionInfo
    ) {
        BusinessResponse response = businessService.updateBusinessIntroductionInfo(id, introductionInfo);
        log.info("Update business introduction info by auth successfully");
        return response;
    }

    @PutMapping("{id}/contactInfo")
    @ResponseStatus(HttpStatus.OK)
    public BusinessResponse updateBusinessContactInfo(
        @PathVariable String id,
        @RequestBody @Valid BusinessContactInfo contactInfo
    ) {
        BusinessResponse response = businessService.updateBusinessContactInfo(id, contactInfo);
        log.info("Update business contact info by auth successfully");
        return response;
    }

    @PutMapping("{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public BusinessResponse updateBusinessStatus(
        @PathVariable String id,
        @RequestBody @Valid StatusUpdateRequest request
    ) {
        BusinessResponse response = businessService.updateBusinessStatus(id, request);
        log.info("Update business status by auth successfully");
        return response;
    }
    
    @PutMapping("{id}/follow")
    @ResponseStatus(HttpStatus.OK)
    public BusinessResponse updateBusinessFollowers(
        @PathVariable String id,
        @RequestBody @Valid BusinessFollower request
    ) {
        BusinessResponse response = businessService.updateBusinessFollowers(id, request);
        log.info("Update business follow by auth successfully");
        return response;
    }

    @DeleteMapping("{id}/open")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void openDeleteBusinessById(@PathVariable String id) {
        businessService.deleteBusinessById(id,false);
        log.info("Open delete business by auth successfully");
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBusinessById(@PathVariable String id) {
        businessService.deleteBusinessById(id,true);
        log.info("Delete business by auth successfully");
    }

    @GetMapping(
        value = "{id}/profileImage",
        produces = MediaType.IMAGE_PNG_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public byte[] getBusinessProfileImage(@PathVariable String id) {
        byte[] file = businessService.getBusinessProfileImage(id);
        log.info("Get business profile image successfully");
        return file;
    }

    @PostMapping("{id}/profileImage")
    @ResponseStatus(HttpStatus.CREATED)
    public BusinessResponse uploadBusinessProfileImage(
        @PathVariable String id,
        @RequestParam MultipartFile file
    ) {
        BusinessResponse response = businessService.uploadBusinessProfileImage(id, file);
        log.info("Upload business profile image by auth successfully");
        return response;
    }

    @GetMapping(
        value = "{id}/backgroundImage",
        produces = MediaType.IMAGE_PNG_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public byte[] getBusinessBackgroundImage(@PathVariable String id) {
        byte[] file = businessService.getBusinessBackgroundImage(id);
        log.info("Get business background image by auth successfully");
        return file;
    }

    @PostMapping("{id}/backgroundImage")
    @ResponseStatus(HttpStatus.CREATED)
    public BusinessResponse updateBusinessBackgroundImage(
        @PathVariable String id,
        @RequestParam MultipartFile file
    ) {
        BusinessResponse response = businessService.updateBusinessBackgroundImage(id, file);
        log.info("Upload business background image by auth successfully");
        return response;
    }
}
