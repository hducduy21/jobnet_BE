package com.jobnet.application.services.impl;

import com.jobnet.application.dtos.requests.ApplicationCreateRequest;
import com.jobnet.application.dtos.requests.ApplicationStatusUpdateRequest;
import com.jobnet.application.dtos.requests.GetApplicationsFilter;
import com.jobnet.application.dtos.responses.ApplicationResponse;
import com.jobnet.application.mappers.ApplicationMapper;
import com.jobnet.application.models.Application;
import com.jobnet.application.repositories.ApplicationRepository;
import com.jobnet.application.services.IApplicationService;
import com.jobnet.clients.post.PostClient;
import com.jobnet.clients.post.PostResponse;
import com.jobnet.clients.resume.ResumeClient;
import com.jobnet.clients.resume.ResumeResponse;
import com.jobnet.clients.user.dtos.responses.JobSeekerResponse;
import com.jobnet.clients.user.UserClient;
import com.jobnet.common.utils.pagination.PaginationResponse;
import com.jobnet.common.exceptions.DataIntegrityViolationException;
import com.jobnet.common.exceptions.ResourceNotFoundException;
import com.jobnet.common.utils.pagination.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService implements IApplicationService {

    private final ApplicationRepository applicationRepository;
    private final MongoTemplate mongoTemplate;
    private final ApplicationMapper applicationMapper;
    private final UserClient jobSeekerClient;
    private final PostClient postClient;
    private final ResumeClient resumeClient;
    private final static List<Application.EApplicationStatus> ALLOWED_APPLICATION_STATUSES =
        List.of(
            Application.EApplicationStatus.Submitted,
            Application.EApplicationStatus.Reviewed,
            Application.EApplicationStatus.Submitted
        );

    @Override
    public PaginationResponse<List<ApplicationResponse>> getApplications(GetApplicationsFilter filter) {
        Pageable pageable = PaginationUtil.getPageable(filter);
        Query query = new Query();

        if (!StringUtils.isBlank(filter.getJobSeekerId()))
            query.addCriteria(Criteria.where("jobSeekerId").is(filter.getJobSeekerId()));
        if (!StringUtils.isBlank(filter.getRecruiterId())) {
            List<String> postIds = postClient.getPostIdsByRecruiterId(filter.getRecruiterId());
            query.addCriteria(Criteria.where("postId").in(postIds));
        }
        if (filter.getApplicationStatus() != null)
            query.addCriteria(Criteria.where("applicationStatus").is(filter.getApplicationStatus()));
        if (filter.getApplicationStatuses() != null)
            query.addCriteria(Criteria.where("applicationStatus").in(filter.getApplicationStatuses()));
        if (filter.getFromDate() != null)
            query.addCriteria(Criteria.where("createdAt").gte(filter.getFromDate()));
        if (filter.getToDate() != null)
            query.addCriteria(Criteria.where("createdAt").lte(filter.getToDate()));

        Page<Application> page = PaginationUtil.getPage(mongoTemplate, query, pageable, Application.class);
        PaginationResponse<List<ApplicationResponse>> response =
            PaginationUtil.getPaginationResponse(page, this::getApplicationResponse);
        log.info("Get applications by auth: filter={}", filter);
        return response;
    }

    @Override
    public ApplicationResponse getApplicationById(String id) {
        Application application = this.findByIdOrElseThrow(id);
        log.info("Get application by auth: {}", application.toString());
        return getApplicationResponse(application);
    }

    @Override
    public ApplicationResponse createApplication(String jobSeekerId, ApplicationCreateRequest request) {
        if (applicationRepository.existsByPostIdAndJobSeekerIdAndApplicationStatusIn(
            request.getPostId(),
            jobSeekerId,
            ALLOWED_APPLICATION_STATUSES
        ))
            throw new DataIntegrityViolationException("Application already exists.");

        JobSeekerResponse jobSeeker = jobSeekerClient.getJobSeekerById(jobSeekerId);
        PostResponse post = postClient.getPostById(request.getPostId());
        postClient.updatePostTotalApplicationsById(request.getPostId(), 1);
        ResumeResponse resume = resumeClient.getResumeById(request.getResumeId());

        // Save to database
        Application _application = applicationMapper.convertToApplication.apply(request);
        _application.setJobSeekerId(jobSeekerId);
        _application.setCreatedAt(LocalDateTime.now());
        _application.setApplicationStatus(Application.EApplicationStatus.Submitted);
        Application application = applicationRepository.save(_application);

        // Generate response
        ApplicationResponse response = applicationMapper.convertToApplicationResponse.apply(application);
        response.setJobSeeker(jobSeeker);
        response.setPost(post);
        response.setResume(resume);

        log.info("Create application by auth: {}", response);
        return response;
    }

    @Override
    public ApplicationResponse updateApplicationStatus(String id, ApplicationStatusUpdateRequest request) {
        Application application = this.findByIdOrElseThrow(id);

        application.setApplicationStatus(Application.EApplicationStatus.valueOf(request.getApplicationStatus()));
        applicationRepository.save(application);

        ApplicationResponse applicationResponse = this.getApplicationResponse(application);
        log.info("Update application status by auth: {}", applicationResponse);
        return applicationResponse;
    }

    @Override
    public void deleteApplicationById(String id) {
        Application application = findByIdOrElseThrow(id);
        postClient.updatePostTotalApplicationsById(application.getPostId(), -1);
        applicationRepository.deleteById(id);
        log.info("Deleted application by auth: id={}",id);
    }

    @Override
    public boolean isSubmitted(String userId, String postId) {
        return applicationRepository.existsByPostIdAndJobSeekerIdAndApplicationStatusIn(
            postId,
            userId,
            ALLOWED_APPLICATION_STATUSES
        );
    }

    private Application findByIdOrElseThrow(String id) {
        return applicationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Application not found."));
    }

    private ApplicationResponse getApplicationResponse(Application application) {
        JobSeekerResponse jobSeeker = jobSeekerClient.getJobSeekerById(application.getJobSeekerId());
        PostResponse post = postClient.getPostById(application.getPostId());
        ResumeResponse resume = resumeClient.getResumeById(application.getResumeId());

        ApplicationResponse response = applicationMapper.convertToApplicationResponse.apply(application);
        response.setJobSeeker(jobSeeker);
        response.setPost(post);
        response.setResume(resume);
        return response;
    }
}
