package com.example.ecommerceuserservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)  // 객체의 null 필드 노출하지 않기
public class UserResponse {
    private String email;
    private String name;
    private String userId;

    private List<OrderResponse> orders;
}
