package com.jobnet.clients.resume;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("resume")
public interface ResumeClient {
    
    @GetMapping("api/resumes/{id}")
    ResumeResponse getResumeById(@PathVariable String id);
}
