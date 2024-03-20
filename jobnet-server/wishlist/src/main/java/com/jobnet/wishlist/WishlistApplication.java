package com.jobnet.wishlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
    "com.jobnet.wishlist",
    "com.jobnet.common.configs",
    "com.jobnet.common.advice",
    "com.jobnet.common.utils"
})
@EnableFeignClients("com.jobnet.clients")
public class WishlistApplication {

    public static void main(String[] args) {
        SpringApplication.run(WishlistApplication.class, args);
    }

}
