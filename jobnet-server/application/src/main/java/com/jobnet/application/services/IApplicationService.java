package com.jobnet.application.services;

import com.jobnet.application.dtos.requests.ApplicationCreateRequest;
import com.jobnet.application.dtos.requests.ApplicationStatusUpdateRequest;
import com.jobnet.application.dtos.requests.GetApplicationsFilter;
import com.jobnet.application.dtos.responses.ApplicationResponse;
import com.jobnet.common.utils.pagination.PaginationResponse;

import java.util.List;

public interface IApplicationService {

    PaginationResponse<List<ApplicationResponse>> getApplications(GetApplicationsFilter filter);

    ApplicationResponse getApplicationById(String id);

    ApplicationResponse createApplication(String jobSeekerId, ApplicationCreateRequest applicationRequest);

    ApplicationResponse updateApplicationStatus(String id, ApplicationStatusUpdateRequest request);

    void deleteApplicationById(String id);

    boolean isSubmitted(String userId, String postId);
}
