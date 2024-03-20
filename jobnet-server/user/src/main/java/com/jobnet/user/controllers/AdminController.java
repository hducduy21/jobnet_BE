package com.jobnet.user.controllers;

import com.jobnet.common.utils.pagination.PaginationResponse;
import com.jobnet.user.dtos.requests.AdminsGetRequest;
import com.jobnet.user.dtos.responses.AdminResponse;
import com.jobnet.user.services.IAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final IAdminService adminService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponse<List<AdminResponse>> getAdmins(@Valid AdminsGetRequest request) {
        return adminService.getAdmins(request);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdminResponse getAdminById(@PathVariable String id) {
        return adminService.getAdminById(id);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public AdminResponse createAdmin(@RequestBody @Valid AdminRequest request) {
//        return adminService.createAdmin(request);
//    }
//
//    @PutMapping("{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public AdminResponse updateAdmin(@PathVariable String id, @RequestBody @Valid AdminRequest request) {
//        return adminService.updateAdmin(id, request);
//    }
//
//    @DeleteMapping("{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteAdmin(@PathVariable String id) {
//        adminService.deleteAdminById(id);
//    }
}
