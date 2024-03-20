package com.jobnet.user.mappers;

import com.jobnet.user.dtos.requests.AdminRequest;
import com.jobnet.user.dtos.responses.AdminResponse;
import com.jobnet.user.models.Admin;
import com.jobnet.user.models.enums.EGender;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AdminMapper {

    public Function<AdminRequest, Admin> convertToAdmin =
        request ->
            Admin.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .gender(EGender.valueOf(request.getGender()))
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .role(request.getRole())
                .build();

    public Function<Admin, AdminResponse> convertToAdminResponse =
        admin ->
            AdminResponse.builder()
                .id(admin.getId())
                .email(admin.getEmail())
                .name(admin.getName())
                .role(admin.getRole())
                .gender(admin.getGender())
                .phone(admin.getPhone())
                .dateOfBirth(admin.getDateOfBirth())
                .build();
}
