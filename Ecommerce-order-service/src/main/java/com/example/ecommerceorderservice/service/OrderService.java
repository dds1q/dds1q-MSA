package com.example.ecommerceorderservice.service;

import com.example.ecommerceorderservice.domain.Order;
import com.example.ecommerceorderservice.dto.OrderDto;


public interface OrderService {

    OrderDto createOrder( OrderDto orderDetails );
    OrderDto getOrderByOrderId( String orderId );
    Iterable<Order> getOrdersByUserId( String userId );

}
