package com.jobnet.business.services;

import com.jobnet.business.dtos.requests.*;
import com.jobnet.business.dtos.responses.BusinessResponse;
import com.jobnet.clients.business.BusinessRegisterRequest;
import org.springframework.web.multipart.MultipartFile;
import com.jobnet.common.utils.pagination.PaginationResponse;

import java.util.List;

public interface IBusinessService {

    PaginationResponse<List<BusinessResponse>> getBusinesses(GetBusinessesFilter filter);

    BusinessResponse getBusinessById(String id);

    BusinessResponse createBusiness(BusinessRegisterRequest request);

    BusinessResponse updateBusinessGeneralInfo(String id, BusinessGeneralInfo request);

    BusinessResponse updateBusinessIntroductionInfo(String id, BusinessIntroductionInfo request);

    BusinessResponse updateBusinessContactInfo(String id, BusinessContactInfo request);

    BusinessResponse updateBusinessStatus(String id, StatusUpdateRequest request);

    void deleteBusinessById(String id, boolean isDelete);

    BusinessResponse uploadBusinessProfileImage(String id, MultipartFile file);

    byte[] getBusinessProfileImage(String id);

    BusinessResponse updateBusinessBackgroundImage(String id, MultipartFile file);

    byte[] getBusinessBackgroundImage(String id);

    BusinessResponse updateBusinessFollowers(String id, BusinessFollower request);
}
