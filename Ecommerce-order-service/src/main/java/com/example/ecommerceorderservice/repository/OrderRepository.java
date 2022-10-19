package com.example.ecommerceorderservice.repository;

import com.example.ecommerceorderservice.domain.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Iterable<Order> findAllByUserId(String userId);

    Optional<Order> findByOrderId(String orderId);
}
