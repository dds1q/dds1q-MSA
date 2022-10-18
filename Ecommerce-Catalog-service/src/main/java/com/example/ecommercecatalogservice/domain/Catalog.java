package com.example.ecommercecatalogservice.domain;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table( name = "catalog" )
public class Catalog extends Timestamped{

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( nullable = false , length = 120 , unique = true )
    private String productId;
    @Column( nullable = false )
    private String productName;
    @Column( nullable = false )
    private Integer stock;
    @Column( nullable = false )
    private Integer unitPrice;

}
