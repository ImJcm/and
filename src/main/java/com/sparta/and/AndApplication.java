package com.sparta.and;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication//(exclude = SecurityAutoConfiguration.class)
public class AndApplication {

    public static void main(String[] args) {
        SpringApplication.run(AndApplication.class, args);
    }

}
