package com.example.ecommerceorderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// 데이터를 저장하기 위해 어떤 필드가 사용될 것인지 지정
@Data
@AllArgsConstructor
public class Field {

    private String type;
    private boolean optional;
    private String field;

}
