package com.jobnet.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
    "com.jobnet.user",
    "com.jobnet.common.configs",
    "com.jobnet.common.advice",
    "com.jobnet.common.s3",
    "com.jobnet.common.kafka",
    "com.jobnet.common.utils",
    "com.jobnet.common.i18n",
})
@EnableFeignClients("com.jobnet.clients")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
