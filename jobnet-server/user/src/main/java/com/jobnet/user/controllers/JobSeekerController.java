package com.jobnet.user.controllers;

import com.jobnet.common.utils.pagination.PaginationResponse;
import com.jobnet.user.dtos.requests.JobSeekerBusinessFollowedInfo;
import com.jobnet.user.dtos.requests.JobSeekerPersonalInfo;
import com.jobnet.user.dtos.requests.JobSeekerProfessionInfo;
import com.jobnet.user.dtos.requests.JobSeekersGetRequest;
import com.jobnet.user.dtos.responses.JobSeekerResponse;
import com.jobnet.user.services.IJobSeekerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/jobSeekers")
@RequiredArgsConstructor
public class JobSeekerController {

    private final IJobSeekerService jobSeekerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponse<List<JobSeekerResponse>> getJobSeekers(@Valid JobSeekersGetRequest request) {
        return jobSeekerService.getJobSeekers(request);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public JobSeekerResponse getJobSeekerById(@PathVariable String id) {
        return jobSeekerService.getJobSeekerById(id);
    }

    @PutMapping("{id}/personalInfo")
    @ResponseStatus(HttpStatus.OK)
    public JobSeekerResponse updateJobSeekerPersonalInfo(
        @PathVariable String id,
        @RequestBody @Valid JobSeekerPersonalInfo personalInfo
    ) {
        return jobSeekerService.updateJobSeekerPersonalInfo(id, personalInfo);
    }

    @PutMapping("{id}/professionInfo")
    @ResponseStatus(HttpStatus.OK)
    public JobSeekerResponse updateJobSeekerProfessionInfo(
        @PathVariable String id,
        @RequestBody @Valid JobSeekerProfessionInfo professionInfo
    ) {
        return jobSeekerService.updateJobSeekerProfessionInfo(id, professionInfo);
    }
    
    @PutMapping("{id}/follow")
    @ResponseStatus(HttpStatus.OK)
    public JobSeekerResponse updateJobSeekerBusinessFollowed(
        @PathVariable String id,
        @RequestBody @Valid JobSeekerBusinessFollowedInfo businessFollowedInfo
    ) {
        return jobSeekerService.updateJobSeekerBusinessFollowed(id, businessFollowedInfo);
    }
    

    @DeleteMapping("{id}/open")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void openDeleteJobSeekerById(@PathVariable String id) {
        jobSeekerService.deleteJobSeekerById(id,false);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJobSeekerById(@PathVariable String id) {
        jobSeekerService.deleteJobSeekerById(id,true);
    }

    @GetMapping(
        value = "{id}/profileImage",
        produces = MediaType.IMAGE_PNG_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public byte[] getJobSeekerProfileImage(@PathVariable String id) {
        return jobSeekerService.getJobSeekerProfileImage(id);
    }

    @PostMapping("{id}/profileImage")
    @ResponseStatus(HttpStatus.OK)
    public JobSeekerResponse uploadJobSeekerProfileImage(
        @PathVariable String id,
        @RequestParam MultipartFile file
    ) {
        return jobSeekerService.uploadJobSeekerProfileImage(id, file);
    }

}
