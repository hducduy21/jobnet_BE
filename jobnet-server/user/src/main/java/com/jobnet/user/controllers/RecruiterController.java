package com.jobnet.user.controllers;

import com.jobnet.common.utils.pagination.PaginationResponse;
import com.jobnet.user.dtos.requests.RecruiterBusinessRequest;
import com.jobnet.user.dtos.requests.RecruiterInformation;
import com.jobnet.user.dtos.requests.RecruitersGetRequest;
import com.jobnet.user.dtos.responses.RawRecruiterResponse;
import com.jobnet.user.dtos.responses.RecruiterResponse;
import com.jobnet.user.services.IRecruiterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/recruiters")
@RequiredArgsConstructor
public class RecruiterController {
    private final IRecruiterService recruiterService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponse<List<RecruiterResponse>> getRecruiters(@Valid RecruitersGetRequest request) {
        return recruiterService.getRecruiters(request);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecruiterResponse getRecruiterById(@PathVariable String id) {
        return recruiterService.getRecruiterById(id);
    }

    @GetMapping("{id}/existsById")
    @ResponseStatus(HttpStatus.OK)
    public Boolean existsRecruiterById(@PathVariable String id) {
        return recruiterService.existsById(id);
    }

    @GetMapping("{id}/raw")
    @ResponseStatus(HttpStatus.OK)
    public RawRecruiterResponse getRawRecruiterById(@PathVariable String id) {
        return recruiterService.getRawRecruiterById(id);
    }

    @PutMapping("{id}/profile")
    @ResponseStatus(HttpStatus.OK)
    public RecruiterResponse updateRecruiterProfile(
        @PathVariable String id,
        @RequestBody @Valid RecruiterInformation recruiterInformation
    ) {
        return recruiterService.updateRecruiterProfile(id, recruiterInformation);
    }

    @PutMapping("{id}/businessId")
    @ResponseStatus(HttpStatus.OK)
    public RecruiterResponse updateRecruiterBusinessId(
        @PathVariable String id,
        @RequestBody RecruiterBusinessRequest request
    ) {
        return recruiterService.updateRecruiterBusinessId(id, request);
    }

    @DeleteMapping("{id}/open")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void openDeleteRecruiterById(@PathVariable String id) {
        recruiterService.deleteRecruiterById(id,false);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecruiterById(@PathVariable String id) {
        recruiterService.deleteRecruiterById(id, true);
    }

    @GetMapping(
        value = "{id}/profileImage",
        produces = MediaType.IMAGE_PNG_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public byte[] getRecruiterProfileImage(@PathVariable String id) {
        return recruiterService.getRecruiterProfileImage(id);
    }

    @PostMapping("{id}/profileImage")
    @ResponseStatus(HttpStatus.OK)
    public RecruiterResponse uploadRecruiterProfileImage(
        @PathVariable String id,
        @RequestParam MultipartFile file
    ) {
        return recruiterService.uploadRecruiterProfileImage(id, file);
    }

}
