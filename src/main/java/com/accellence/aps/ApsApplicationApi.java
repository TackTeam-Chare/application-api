package com.accellence.aps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.accellence.aps")
public class ApsApplicationApi {

    public static void main(String[] args) {
        SpringApplication.run(ApsApplicationApi.class, args);
    }
}

