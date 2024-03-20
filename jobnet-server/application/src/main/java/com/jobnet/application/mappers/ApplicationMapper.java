package com.jobnet.application.mappers;

import com.jobnet.application.dtos.requests.ApplicationCreateRequest;
import com.jobnet.application.dtos.responses.ApplicationResponse;
import com.jobnet.application.models.Application;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ApplicationMapper {
    public Function<ApplicationCreateRequest, Application> convertToApplication =
        request->
            Application.builder()
                .postId(request.getPostId())
                .resumeId(request.getResumeId())
                .build();

    public Function<Application, ApplicationResponse> convertToApplicationResponse =
        application ->
            ApplicationResponse.builder()
                .id(application.getId())
                .createdAt(application.getCreatedAt())
                .applicationStatus(application.getApplicationStatus())
                .build();
}
