package com.eedo.gdg.domain.repository;

import com.eedo.gdg.domain.entity.*;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Log4j2
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    // 오더 생성 비즈니스 테스트
    @Test
    public void createOrder() throws Exception{
        //given
        Member testMember = Member.builder()
                .name("이도연")
                .address(
                        Address.builder()
                                .city("성남시")
                                .street_address("장미로55")
                                .zipcode(13441).build()
                ).build();

        Order testOrder = Order.builder()
                .status(OrderStatus.ORDER)
                .orderDate(Date.valueOf(LocalDate.now()))
                .build();

        Delivery deliveryAddress = Delivery.builder()
                .address(Address.builder()
                        .city(testMember.getAddress().getCity())
                        .street_address(testMember.getAddress().getStreet_address())
                        .zipcode(testMember.getAddress().getZipcode())
                        .build())
                .build();

        Member member = memberRepository.save(testMember);
        Order order = orderRepository.save(testOrder);
        Delivery delivery = deliveryRepository.save(deliveryAddress);

        //when
        order.createOrder(member,delivery);

        //then
        Assertions.assertThat(order.getMember().getName()).isEqualTo("이도연");
        Assertions.assertThat(delivery.getOrder()).isEqualTo(order);

        order.getMember().getOrders().forEach(result ->
                log.info(result.getOrderDate()));

        log.info(order.getId());
        log.info(order.getDelivery().getAddress().getCity());

    }

    // 오더 찾기
    @Test
    public void findById() throws Exception{

        //given

        for (int i = 0; i < 30; i++) {
            Order testOrder = Order.builder()
                    .status(OrderStatus.ORDER)
                    .member(Member.builder()
                            .name("name_"+i).build())
                    .orderDate(Date.valueOf(LocalDate.now()))
                    .build();

            Order save = orderRepository.save(testOrder);
        }

        //when
        Optional<Order> byId = orderRepository.findById(20L);
        Order order = byId.orElseThrow();

        //then
        Assertions.assertThat(order.getMember().getName()).isEqualTo("name_19");
    }



}