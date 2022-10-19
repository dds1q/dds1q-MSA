package com.example.ecommerceorderservice.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Table( name = "orders" )
@Entity
public class Order extends Timestamped implements Serializable {
    /*직렬화 사용목적 : 가지고 있는 객체를 네트워크 전송하거나 데이터베이스에 보관하기위해 마샬링 작업*/

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( nullable = false , length = 120 , unique = true )
    private String productId;
    @Column( nullable = false )
    private Integer qty;
    @Column( nullable = false )
    private Integer unitPrice;
    @Column( nullable = false )
    private Integer totalPrice;

    @Column( nullable = false )
    private String userId;
    @Column( nullable = false , unique = true )
    private String orderId;

}
