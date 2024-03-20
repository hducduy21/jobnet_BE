package com.jobnet.resume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
    "com.jobnet.resume",
    "com.jobnet.common.configs",
    "com.jobnet.common.advice",
    "com.jobnet.common.s3"
})
@EnableFeignClients("com.jobnet.clients")
public class ResumeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeApplication.class, args);
    }

}
