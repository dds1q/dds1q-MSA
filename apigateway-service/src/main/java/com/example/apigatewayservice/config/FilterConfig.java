package com.example.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {

    // yml 파일에서 하던 동작을 자바 클래스로 옮겨놓음
//    @Bean
    public RouteLocator gatewayRoutes( RouteLocatorBuilder builder ){
        return builder.routes()
            .route( r -> r.path("/first-service/**")
                .filters( f -> f.addRequestHeader("first-request","first-request-header")
                                .addResponseHeader("first-response","first-response-header") )
                .uri("http://localhost:8081") )
            .route( r -> r.path("/second-service/**")
                .filters( f -> f.addRequestHeader("second-request","second-request-header")
                    .addResponseHeader("second-response","second-response-header") )
                .uri("http://localhost:8082") )
            .build();
    }
}
