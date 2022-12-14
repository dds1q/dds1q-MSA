package com.example.ecommerceuserservice.clientservice;

import com.example.ecommerceuserservice.dto.OrderResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient( name = "order-service" )
public interface OrderServiceClient {

    @GetMapping("/order-service/{userId}/orders_ng")
    List<OrderResponse> getOrders( @PathVariable String userId );

}
