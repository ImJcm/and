package com.sparta.and;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@EnableJpaAuditing
@SpringBootApplication
@EnableJpaRepositories
public class AndApplication {

    public static void main(String[] args) {
        SpringApplication.run(AndApplication.class, args);
    }
}
