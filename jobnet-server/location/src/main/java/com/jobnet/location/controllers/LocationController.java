package com.jobnet.location.controllers;

import com.jobnet.location.models.Province;
import com.jobnet.location.services.ILocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/location")
@RequiredArgsConstructor
public class LocationController {

    private final ILocationService locationService;

    @GetMapping("provinces")
    @ResponseStatus(HttpStatus.OK)
    public List<Province> getProvinces() {
        return locationService.getProvinces();
    }
}
