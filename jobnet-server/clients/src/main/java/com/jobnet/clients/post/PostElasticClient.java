package com.jobnet.clients.post;

import com.jobnet.common.dtos.PostElasticResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "elastic-service", url = "http://localhost:9201", path = "/api/post")
public interface PostElasticClient {
	@PostMapping()
	PostElasticResponse createPostElastic(@RequestBody PostElasticResponse postElasticResponse);
}
