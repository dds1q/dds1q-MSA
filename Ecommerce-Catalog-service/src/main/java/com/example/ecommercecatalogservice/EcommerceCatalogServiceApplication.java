package com.example.ecommercecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EcommerceCatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceCatalogServiceApplication.class, args);
	}

}
