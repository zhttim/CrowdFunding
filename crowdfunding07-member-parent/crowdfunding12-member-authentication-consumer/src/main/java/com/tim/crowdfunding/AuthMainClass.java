package com.tim.crowdfunding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AuthMainClass {
    public static void main(String[] args) {
        SpringApplication.run(AuthMainClass.class, args);
    }
}