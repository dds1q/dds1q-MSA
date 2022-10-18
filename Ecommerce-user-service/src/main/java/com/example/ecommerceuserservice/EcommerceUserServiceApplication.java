package com.example.ecommerceuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class EcommerceUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceUserServiceApplication.class, args);
    }

    @Bean   // BCryptPasswordEncoder 타입 사용할 수 있도록 등록
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
