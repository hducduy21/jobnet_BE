package com.jobnet.clients.business;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("business")
public interface BusinessClient {

    @GetMapping("api/businesses/{id}")
    BusinessResponse getBusinessById(@PathVariable String id);

    @PostMapping("api/businesses")
    BusinessResponse createBusiness(BusinessRegisterRequest request);

}
