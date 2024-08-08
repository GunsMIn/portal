package com.example.demoportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DemoPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoPortalApplication.class, args);
    }

}
