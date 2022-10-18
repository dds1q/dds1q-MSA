package com.example.ecommercecatalogservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Date;
import javax.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class CatalogResponse {

    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;
    private Date createdAt;

}
