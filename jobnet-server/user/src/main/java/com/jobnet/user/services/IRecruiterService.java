package com.jobnet.user.services;

import com.jobnet.common.utils.pagination.PaginationResponse;
import com.jobnet.user.dtos.requests.*;
import com.jobnet.user.dtos.responses.RawRecruiterResponse;
import com.jobnet.user.dtos.responses.RecruiterResponse;
import com.jobnet.user.models.VerificationOTP;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRecruiterService {

    PaginationResponse<List<RecruiterResponse>> getRecruiters(RecruitersGetRequest request);

    RecruiterResponse getRecruiterById(String id);

    RawRecruiterResponse getRawRecruiterById(String id);
    Boolean existsById(String id);

    VerificationOTP createRecruiterWithNewBusiness(RecruiterWithNewBusinessRegisterRequest request);

    VerificationOTP createRecruiterWithSelectedBusiness(RecruiterWithSelectedBusinessRegisterRequest request);

    RecruiterResponse updateRecruiterProfile(String id, RecruiterInformation recruiterInformation);

    RecruiterResponse updateRecruiterBusinessId(String id, RecruiterBusinessRequest request);

    void deleteRecruiterById(String id,boolean locked);

    RecruiterResponse uploadRecruiterProfileImage(String id, MultipartFile file);

    byte[] getRecruiterProfileImage(String id);
}
