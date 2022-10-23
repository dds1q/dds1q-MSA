package com.example.ecommercecatalogservice.messagequeue;

import com.example.ecommercecatalogservice.domain.Catalog;
import com.example.ecommercecatalogservice.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final CatalogRepository catalogrepository;

    @KafkaListener( topics = "example-catalog-topic" )
    public void updateQty( String kafkaMessage ){
        log.info("Kafka Message: ->" , kafkaMessage );

        Map<Object,Object> map = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        try{
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch ( JsonProcessingException e ) {
            e.printStackTrace();
        }
        // 상품의 id로 상품정보 가져온다.
        Catalog catalog = catalogrepository.findByProductId( (String)map.get("productId") )
            .orElseThrow( () -> new ObjectNotFoundException( HttpStatus.NOT_FOUND , "PRODUCT_NOT_FOUND"));

        // 수량 업데이트
        catalog.setStock( catalog.getStock() - (Integer)map.get("qty"));
        catalogrepository.save( catalog );

    }

}
