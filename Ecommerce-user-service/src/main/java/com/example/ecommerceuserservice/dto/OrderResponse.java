package com.example.ecommerceuserservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class OrderResponse{

    private String orderId;

    private String productId;
    private Integer qty;    // quantity 수량
    private Integer unitPrice;  // 단가
    private Integer totalPrice; // 총가격
    private LocalDateTime createdAt;

}
