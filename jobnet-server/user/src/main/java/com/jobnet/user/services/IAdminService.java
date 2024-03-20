package com.jobnet.user.services;

import com.jobnet.common.utils.pagination.PaginationResponse;
import com.jobnet.user.dtos.requests.AdminRequest;
import com.jobnet.user.dtos.requests.AdminsGetRequest;
import com.jobnet.user.dtos.responses.AdminResponse;

import java.util.List;

public interface IAdminService {

    AdminResponse createAdmin(AdminRequest request);

    PaginationResponse<List<AdminResponse>> getAdmins(AdminsGetRequest request);

    AdminResponse getAdminById(String id);

    void deleteAdminById(String id);

    AdminResponse updateAdmin(String id, AdminRequest request);

}
