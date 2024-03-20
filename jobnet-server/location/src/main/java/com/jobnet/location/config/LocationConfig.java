package com.jobnet.location.config;

import com.jobnet.location.models.Province;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "location")
@Getter
@Setter
public class LocationConfig {

    private List<Province> provinces;
}
