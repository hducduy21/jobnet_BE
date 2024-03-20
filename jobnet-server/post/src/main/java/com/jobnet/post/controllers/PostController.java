package com.jobnet.post.controllers;

import com.jobnet.common.utils.pagination.PaginationResponse;
import com.jobnet.post.dtos.requests.*;
import com.jobnet.post.dtos.responses.PostResponse;
import com.jobnet.post.services.IPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final IPostService postService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponse<List<PostResponse>> getPosts(
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "10") Integer pageSize,
        @RequestParam(defaultValue = "createdAt-desc") List<String> sortBy,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String categoryId,
        @RequestParam(required = false) String professionId,
        @RequestParam(required = false) BigInteger minSalary,
        @RequestParam(required = false) BigInteger maxSalary,
        @RequestParam(required = false) String provinceName,
        @RequestParam(required = false) String workingFormat,
        @RequestParam(required = false) String recruiterId,
        @RequestParam(required = false) String businessId,
        @RequestParam(required = false) List<String> activeStatus,
        @RequestParam(required = false) LocalDate fromDate,
        @RequestParam(required = false) LocalDate toDate,
        @RequestParam(required = false) Boolean isExpired
    ) {
        PaginationResponse<List<PostResponse>> paginationResponse = postService.getPosts(
            page,
            pageSize,
            sortBy,
            search,
            categoryId,
            professionId,
            minSalary,
            maxSalary,
            provinceName,
            workingFormat,
            recruiterId,
            businessId,
            activeStatus,
            fromDate,
            toDate,
            isExpired
        );
        log.info("Get posts successfully");
        return paginationResponse;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse getPostById(@PathVariable String id) {
        PostResponse postResponse = postService.getPostById(id);
        log.info("Get post by id successfully");
        return postResponse;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse createPost(
        @RequestHeader String userId,
        @ModelAttribute @Valid PostCreateRequest postCreateRequest
    ) {
        log.info("Create post: {}", userId);
        log.info("applicationDeadline post: {}", postCreateRequest.getApplicationDeadline());
        PostResponse postResponse = postService.createPost(userId, postCreateRequest);
        log.info("Create post successfully");
        return postResponse;
    }

    @PutMapping("{id}/headingInfo")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse updatePostHeadingInfo(
        @PathVariable String id,
        @RequestBody @Valid PostHeadingInfoUpdateRequest request
    ) {
        PostResponse response = postService.updatePostHeadingInfo(id, request);
        log.info("Update post heading info successfully");
        return response;
    }

    @PutMapping("{id}/detailedInfo")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse updatePostDetailedInfo(
        @PathVariable String id,
        @RequestBody @Valid PostDetailedInfoUpdateRequest request
    ) {
        PostResponse response = postService.updatePostDetailedInfo(id, request);
        log.info("Update post general info successfully");
        return response;
    }

    @PutMapping("{id}/generalInfo")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse updatePostGeneralInfo(
        @PathVariable String id,
        @RequestBody @Valid PostGeneralInfoUpdateRequest request
    ) {
        PostResponse response = postService.updatePostGeneralInfo(id, request);
        log.info("Update post general info successfully");
        return response;
    }

    @PutMapping("{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse updatePostStatus(
        @PathVariable String id,
        @RequestBody @Valid PostActiveStatusUpdateRequest request
    ) {
        PostResponse response = postService.updatePostActiveStatus(id, request);
        log.info("Update post status successfully");
        return response;
    }

    @GetMapping(
        value = "{id}/jd",
        produces = MediaType.APPLICATION_PDF_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public byte[] getPostJd(@PathVariable String id) {
        byte[] fileData = postService.getPostJd(id);
        log.info("Get post JD successfully");
        return fileData;
    }

    @GetMapping("ids")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getPostIdsByRecruiterId(
        @RequestParam String recruiterId
    ) {
        List<String> postIds = postService.getPostIdsByRecruiterId(recruiterId);
        log.info("Get post IDs successfully");
        return postIds;
    }

    @PutMapping("{id}/totalApplications")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse updatePostTotalApplicationsById(
        @PathVariable String id,
        @RequestBody Integer number
    ) {
        PostResponse postResponse = postService.updatePostTotalApplicationsById(id, number);
        log.info("Update post total application by id successfully");
        return postResponse;
    }
}
