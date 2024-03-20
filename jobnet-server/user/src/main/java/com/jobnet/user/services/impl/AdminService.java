package com.jobnet.user.services.impl;

import com.jobnet.common.utils.pagination.PaginationResponse;
import com.jobnet.common.exceptions.DataIntegrityViolationException;
import com.jobnet.common.exceptions.ResourceNotFoundException;
import com.jobnet.common.utils.pagination.PaginationUtil;
import com.jobnet.user.dtos.requests.AdminRequest;
import com.jobnet.user.dtos.requests.AdminsGetRequest;
import com.jobnet.user.dtos.responses.AdminResponse;
import com.jobnet.user.mappers.AdminMapper;
import com.jobnet.user.models.Admin;
import com.jobnet.user.models.enums.EGender;
import com.jobnet.user.repositories.AdminRepository;
import com.jobnet.user.services.IAdminService;
import com.jobnet.common.i18n.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService implements IAdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;
    private final MessageUtil messageUtil;

    @Override
    public AdminResponse createAdmin(AdminRequest request) {
        if (adminRepository.existsByEmail(request.getEmail()))
            throw new DataIntegrityViolationException(messageUtil.getMessage("error.inUse.email"));
        if (adminRepository.existsByPhone(request.getPhone()))
            throw new DataIntegrityViolationException(messageUtil.getMessage("error.inUse.phone"));

        Admin _admin = adminMapper.convertToAdmin.apply(request);
        _admin.setPassword(passwordEncoder.encode(_admin.getPassword()));
        Admin admin = adminRepository.save(_admin);

        log.info(messageUtil.getMessage("success.create.admin", request, admin));
        return this.getAdminResponse(admin);
    }

    @Override
    public PaginationResponse<List<AdminResponse>> getAdmins(AdminsGetRequest request) {
        Pageable pageable = PaginationUtil.getPageable(request.getPage(), request.getPageSize(), request.getSortBys());
        Page<Admin> adminPage = adminRepository.findAll(pageable);
        PaginationResponse<List<AdminResponse>> response =
            PaginationUtil.getPaginationResponse(adminPage, this::getAdminResponse);

        log.info(messageUtil.getMessage("success.getAll.admin", request));
        return response;
    }

    private Admin findByIdOrElseThrow(String id) {
        return adminRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("error.notFound.admin")));
    }

    @Override
    public AdminResponse getAdminById(String id) {
        Admin admin = this.findByIdOrElseThrow(id);
        log.info(messageUtil.getMessage("success.get.admin", id, admin));
        return this.getAdminResponse(admin);
    }

    @Override
    public void deleteAdminById(String id) {
        if (!adminRepository.existsById(id))
            throw new ResourceNotFoundException(messageUtil.getMessage("error.notFound.admin"));
        adminRepository.deleteById(id);
        log.info(messageUtil.getMessage("success.delete.admin", id));
    }

    @Override
    public AdminResponse updateAdmin(String id, AdminRequest adminRequest) {
        Admin admin = this.findByIdOrElseThrow(id);

        if (adminRepository.existsByIdNotAndEmail(id, adminRequest.getEmail()))
            throw new DataIntegrityViolationException(messageUtil.getMessage("error.inUse.email"));
        if (adminRepository.existsByIdNotAndPhone(id, adminRequest.getPhone()))
            throw new DataIntegrityViolationException(messageUtil.getMessage("error.inUse.phone"));

        admin.setEmail(adminRequest.getEmail());
        admin.setPassword(passwordEncoder.encode(adminRequest.getPassword()));
        admin.setName(adminRequest.getName());
        admin.setGender(EGender.valueOf(adminRequest.getGender()));
        admin.setPhone(adminRequest.getPhone());
        admin.setDateOfBirth(adminRequest.getDateOfBirth());
        adminRepository.save(admin);

        log.info(messageUtil.getMessage("success.update.admin", id, adminRequest, admin));
        return this.getAdminResponse(admin);
    }

    private AdminResponse getAdminResponse(Admin admin) {
        return adminMapper.convertToAdminResponse.apply(admin);
    }
}
