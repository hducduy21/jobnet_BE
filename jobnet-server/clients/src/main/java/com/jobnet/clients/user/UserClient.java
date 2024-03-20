package com.jobnet.clients.user;

import com.jobnet.clients.user.dtos.responses.JobSeekerResponse;
import com.jobnet.clients.user.dtos.responses.RawRecruiterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user")
public interface UserClient {

    @GetMapping("api/jobSeekers/{id}")
    JobSeekerResponse getJobSeekerById(@PathVariable String id);

    @GetMapping("api/recruiters/{id}/raw")
    RawRecruiterResponse getRawRecruiterById(@PathVariable String id);

    @GetMapping("api/recruiters/{id}/existsById")
    Boolean existsRecruiterById(@PathVariable String id);
}
