package com.jobnet.location;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
    "com.jobnet.location",
    "com.jobnet.common.configs",
    "com.jobnet.common.advice",
    "com.jobnet.common.i18n"
})
public class LocationApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocationApplication.class, args);
    }
}
