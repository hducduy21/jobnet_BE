package com.jobnet.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
    "com.jobnet.application",
    "com.jobnet.common.configs",
    "com.jobnet.common.advice",
    "com.jobnet.common.utils"
})
@EnableFeignClients("com.jobnet.clients")
public class ApplApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApplApplication.class, args);
    }

}
