package com.jobnet.user.mappers;

import com.jobnet.user.dtos.requests.RecruiterWithNewBusinessRegisterRequest;
import com.jobnet.user.dtos.requests.RecruiterWithSelectedBusinessRegisterRequest;
import com.jobnet.user.dtos.responses.RawRecruiterResponse;
import com.jobnet.user.dtos.responses.RecruiterResponse;
import com.jobnet.user.models.Recruiter;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RecruiterMapper {

    public Function<RecruiterWithNewBusinessRegisterRequest, Recruiter>
            convertRecruiterWithNewBusinessRegisterRequestToRecruiter =
            request ->
                    Recruiter.builder()
                            .email(request.getEmail())
                            .phone(request.getPhone())
                            .name(request.getName())
                            .build();

    public Function<RecruiterWithSelectedBusinessRegisterRequest, Recruiter>
            convertRecruiterWithSelectedBusinessRegisterRequestToRecruiter =
            request ->
                    Recruiter.builder()
                            .email(request.getEmail())
                            .phone(request.getPhone())
                            .name(request.getName())
                            .build();


    public Function<Recruiter, RecruiterResponse> convertToRecruiterResponse =
            recruiter ->
                    RecruiterResponse.builder()
                            .id(recruiter.getId())
                            .email(recruiter.getEmail())
                            .name(recruiter.getName())
                            .role(recruiter.getRole())
                            .phone(recruiter.getPhone())
                            .profileImageId(recruiter.getProfileImageId())
                            .activeBusiness(recruiter.isActiveBusiness())
                            .nation(recruiter.getNation())
                            .locked(recruiter.getLocked())
                            .build();

    public Function<Recruiter, RawRecruiterResponse> convertToRawRecruiterResponse =

            recruiter ->
                    RawRecruiterResponse.builder()
                            .id(recruiter.getId())
                            .email(recruiter.getEmail())
                            .name(recruiter.getName())
                            .role(recruiter.getRole())
                            .phone(recruiter.getPhone())
                            .profileImageId(recruiter.getProfileImageId())
                            .businessId(recruiter.getBusinessId())
                            .activeBusiness(recruiter.isActiveBusiness())
                            .nation(recruiter.getNation())
                            .locked(recruiter.getLocked())
                            .build();
}