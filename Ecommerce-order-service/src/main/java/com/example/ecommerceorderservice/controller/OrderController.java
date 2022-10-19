package com.example.ecommerceorderservice.controller;

import com.example.ecommerceorderservice.domain.Order;
import com.example.ecommerceorderservice.dto.OrderDto;
import com.example.ecommerceorderservice.dto.OrderRequest;
import com.example.ecommerceorderservice.dto.OrderResponse;
import com.example.ecommerceorderservice.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.matcher.StringMatcher.Mode;
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

    @GetMapping("/health_check")    // 상태체크, 포트확인
    public String status(){
        return String.format( "It's Working in Order Service on PORT %s", env.getProperty("local.server.port") );
    }

    @PostMapping("/orders")  // 주문 생성
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request ){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map( request , OrderDto.class );

        OrderResponse orderResponse = mapper.map( orderService.createOrder( orderDto ) , OrderResponse.class );

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId( @PathVariable String userId ){

        Iterable<Order> orders = orderService.getOrdersByUserId( userId );
        List<OrderResponse> result = new ArrayList<>();

        orders.forEach( v -> {
            result.add( new ModelMapper().map( v , OrderResponse.class ) );
        });
        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponse> getOrderByOrderId( @PathVariable String orderId ){

        OrderDto orderDto = orderService.getOrderByOrderId( orderId);

        return ResponseEntity.status( HttpStatus.OK )
            .body( new ModelMapper().map( orderDto , OrderResponse.class ));
    }

}
