package com.eedo.gdg.domain.service;

import com.eedo.gdg.domain.dto.DeliveryDto;
import com.eedo.gdg.domain.dto.OrderDto;
import com.eedo.gdg.domain.entity.Delivery;
import com.eedo.gdg.domain.entity.Member;
import com.eedo.gdg.domain.entity.Order;
import com.eedo.gdg.domain.entity.OrderStatus;
import com.eedo.gdg.domain.repository.DeliveryRepository;
import com.eedo.gdg.domain.repository.MemberRepository;
import com.eedo.gdg.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryService deliveryService;


    @Override
    public Long order(OrderDto dto) {

        // member 가져오기
        Optional<Member> byId = memberRepository.findById(dto.getMember_id());
        Member member = byId.orElseThrow();

        //delivery 가져오기
        Optional<Delivery> byId1 = deliveryRepository.findById(dto.getAddress_id());
        Delivery delivery = byId1.orElseThrow();

        // order 새로 생성
        Order order = Order.builder()
                .status(OrderStatus.ORDER)
                .orderDate(Date.valueOf(LocalDate.now()))
                .build();

        // 비즈니스 로직 적용

        order.createOrder(member,delivery);
        Order save = orderRepository.save(order);

        return save.getId();
    }

    @Override
    public OrderDto find(Long id) {
        Optional<Order> byId = orderRepository.findById(id);
        Order order = byId.orElseThrow();

        return entityToDTO(order);
    }

    @Override
    public Order complete(Long id) {
        Optional<Order> byId = orderRepository.findById(id);
        Order order = byId.orElseThrow();

        order.complete();
        return order;
    }

    @Override
    public Order cancel(Long id) {
        Optional<Order> byId = orderRepository.findById(id);
        Order order = byId.orElseThrow();

        if (order.getStatus() == OrderStatus.COMPLETE) {
            return order;

        }

        order.cancel();
        return order;

    }

    @Override
    public Order updateAddress(Long id,DeliveryDto dto) {
        Optional<Order> byId1 = orderRepository.findById(id);
        Order order = byId1.orElseThrow();

        Long address_id = order.getDelivery().getId();
        dto.setId(address_id);

        Delivery update = deliveryService.update(dto);
        order.update(update);

        return order;
    }
}
