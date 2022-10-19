package com.example.ecommerceorderservice.service;


import com.example.ecommerceorderservice.domain.Order;
import com.example.ecommerceorderservice.dto.OrderDto;
import com.example.ecommerceorderservice.repository.OrderRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override       // 주문 생성
    public OrderDto createOrder( OrderDto orderDto ) {

        orderDto.setOrderId( UUID.randomUUID().toString() );
        orderDto.setTotalPrice( orderDto.getQty() * orderDto.getUnitPrice() );

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);   //mapper 규칙:엄격하게로 설정
        Order order = mapper.map( orderDto , Order.class );

        orderRepository.save( order );

        return orderDto;
    }

    @Override       // 주문번호로 주문 찾기
    public OrderDto getOrderByOrderId(String orderId) {

        Order order = orderRepository.findByOrderId( orderId ).orElseThrow(
            () -> new ObjectNotFoundException(HttpStatus.NOT_FOUND , "ORDER NOT FOUND ")
        );

        return new ModelMapper().map( order , OrderDto.class );
    }

    @Override   // 회원의 주문리스트 모두 찾기
    public Iterable<Order> getOrdersByUserId(String userId) {

        return orderRepository.findAllByUserId( userId );
    }
}
