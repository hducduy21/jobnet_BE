package com.jobnet.clients.post;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("post")
public interface PostClient {

    @GetMapping("api/posts/{id}")
    PostResponse getPostById(@PathVariable String id);

    @GetMapping("api/posts/ids")
    List<String> getPostIdsByRecruiterId(@RequestParam String recruiterId);
    
    @PutMapping("api/posts/{id}/totalApplications")
    PostResponse updatePostTotalApplicationsById(@PathVariable String id, @RequestBody Integer number);
    
}
