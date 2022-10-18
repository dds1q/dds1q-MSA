package com.example.ecommerceuserservice.dto;

import java.util.Date;

public class OrderResponse {

    private String orderId;

    private String productId;
    private Integer qty;    // quantity 수량
    private Integer unitPrice;  // 단가
    private Integer totalPrice; // 총가격
    private Date createdAt;

}
