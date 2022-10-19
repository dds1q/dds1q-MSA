package com.example.ecommerceorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EcommerceOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceOrderServiceApplication.class, args);
    }

}
