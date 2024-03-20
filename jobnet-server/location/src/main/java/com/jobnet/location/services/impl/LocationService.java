package com.jobnet.location.services.impl;

import com.jobnet.common.i18n.MessageUtil;
import com.jobnet.location.config.LocationConfig;
import com.jobnet.location.models.Province;
import com.jobnet.location.services.ILocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService implements ILocationService {

    private final LocationConfig locationConfig;
    private final MessageUtil messageUtil;

    @Override
    @Cacheable(value = "provincesCache", sync = true)
    public List<Province> getProvinces() {
        List<Province> provinces = locationConfig.getProvinces();
        log.info(messageUtil.getMessage("success.getAll.province"));
        return provinces;
    }
}
