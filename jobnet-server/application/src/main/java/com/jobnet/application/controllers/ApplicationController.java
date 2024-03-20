package com.jobnet.application.controllers;

import com.jobnet.application.dtos.requests.ApplicationCreateRequest;
import com.jobnet.application.dtos.requests.ApplicationStatusUpdateRequest;
import com.jobnet.application.dtos.requests.GetApplicationsFilter;
import com.jobnet.application.dtos.responses.ApplicationResponse;
import com.jobnet.application.services.IApplicationService;
import com.jobnet.common.utils.pagination.PaginationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/applications")
@RequiredArgsConstructor
@Slf4j
public class ApplicationController {

    private final IApplicationService applicationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponse<List<ApplicationResponse>> getApplications(GetApplicationsFilter filter) {
        PaginationResponse<List<ApplicationResponse>> response = applicationService.getApplications(filter);
        log.info("Get applications by auth successfully");
        return response;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse getApplicationById(@PathVariable String id) {
        ApplicationResponse response = applicationService.getApplicationById(id);
        log.info("Get application by auth successfully");
        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse createApplication(
        @RequestHeader String userId,
        @RequestBody @Valid ApplicationCreateRequest request
    ) {
        ApplicationResponse response = applicationService.createApplication(userId, request);
        log.info("Create application by auth successfully");
        return response;
    }

    @PutMapping("{id}/applicationStatus")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationResponse updateApplicationStatus(
        @PathVariable String id,
        @RequestBody @Valid ApplicationStatusUpdateRequest request
    ) {
        ApplicationResponse response = applicationService.updateApplicationStatus(id, request);
        log.info("Update application status by auth successfully");
        return response;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteApplicationById(@PathVariable String id) {
        applicationService.deleteApplicationById(id);
        log.info("Delete application by auth successfully");
    }

    @GetMapping("isSubmitted")
    @ResponseStatus(HttpStatus.OK)
    public boolean isSubmitted(
        @RequestHeader String userId,
        @RequestParam String postId
    ) {
        boolean isSubmitted = applicationService.isSubmitted(userId, postId);
        log.info("Exist application by auth successfully");
        return isSubmitted;
    }
}
