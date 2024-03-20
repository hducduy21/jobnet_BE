package com.jobnet.post.services;

import com.jobnet.common.utils.pagination.PaginationResponse;
import com.jobnet.post.dtos.requests.*;
import com.jobnet.post.dtos.responses.PostResponse;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

public interface IPostService {

    PaginationResponse<List<PostResponse>> getPosts(
        Integer page,
        Integer pageSize,
        List<String> sortBy,
        String search,
        String categoryId,
        String professionId,
        BigInteger minSalary,
        BigInteger maxSalary,
        String provinceName,
        String workingFormat,
        String recruiterId,
        String businessId,
        List<String> activeStatus,
        LocalDate fromDate,
        LocalDate toDate,
        Boolean isExpired
    );

    PostResponse getPostById(String id);

    PostResponse createPost(String userId, PostCreateRequest postCreateRequest);

    PostResponse updatePostHeadingInfo(String id, PostHeadingInfoUpdateRequest request);

    PostResponse updatePostDetailedInfo(String id, PostDetailedInfoUpdateRequest request);

    PostResponse updatePostGeneralInfo(String id, PostGeneralInfoUpdateRequest request);

    PostResponse updatePostActiveStatus(String id, PostActiveStatusUpdateRequest request);

    byte[] getPostJd(String id);

    List<String> getPostIdsByRecruiterId(String recruiterId);

    PostResponse updatePostTotalApplicationsById(String id, int number);

}
