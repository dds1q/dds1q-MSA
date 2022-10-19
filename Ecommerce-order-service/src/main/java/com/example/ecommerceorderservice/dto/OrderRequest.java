package com.example.ecommerceorderservice.dto;

import java.util.Date;
import lombok.Data;

@Data
public class OrderRequest {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private String userId;

}
