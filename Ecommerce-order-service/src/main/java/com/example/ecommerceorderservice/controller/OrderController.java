package com.example.ecommerceorderservice.controller;

import com.example.ecommerceorderservice.domain.Order;
import com.example.ecommerceorderservice.dto.OrderDto;
import com.example.ecommerceorderservice.dto.OrderRequest;
import com.example.ecommerceorderservice.dto.OrderResponse;
import com.example.ecommerceorderservice.messagequeue.KafkaProducer;
import com.example.ecommerceorderservice.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final Environment env;

    private final KafkaProducer kafkaProducer;  // 메시지 전송

    @GetMapping("/health_check")    // 상태체크, 포트확인
    public String status(){
        return String.format( "It's Working in Order Service on PORT %s", env.getProperty("local.server.port") );
    }

    @PostMapping("/orders")  // 주문 생성
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request ){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderDto orderDto = mapper.map( request , OrderDto.class );
       /* JPA */

//        OrderResponse orderResponse = mapper.map( orderService.createOrder( orderDto ) , OrderResponse.class );

        /* kafka */
        orderDto.setOrderId( UUID.randomUUID().toString() );
        orderDto.setTotalPrice( request.getQty() * request.getUnitPrice() );

        /* send new order to the kafka*/
        // topic 명은 카탈로그서비스의 kafkaListener에 설정한 topics 의 이름과 일치해야한다.
        kafkaProducer.send("example-catalog-topic" , orderDto );

        OrderResponse orderResponse = mapper.map( orderDto , OrderResponse.class );

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

    @GetMapping("/{userId}/orders") // 회원의 주문정보들 조회하기
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId( @PathVariable String userId ){

        Iterable<Order> orders = orderService.getOrdersByUserId( userId );
        List<OrderResponse> result = new ArrayList<>();

        orders.forEach( v -> {
            result.add( new ModelMapper().map( v , OrderResponse.class ) );
        });
        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    @GetMapping("/orders/{orderId}")    // 주문번호로 주문 조회
    public ResponseEntity<OrderResponse> getOrderByOrderId( @PathVariable String orderId ){

        OrderDto orderDto = orderService.getOrderByOrderId( orderId);

        return ResponseEntity.status( HttpStatus.OK )
            .body( new ModelMapper().map( orderDto , OrderResponse.class ));
    }

}
