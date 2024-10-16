package com.eedo.gdg.domain.service;

import com.eedo.gdg.domain.dto.DeliveryDto;
import com.eedo.gdg.domain.dto.OrderDto;
import com.eedo.gdg.domain.entity.*;
import com.eedo.gdg.domain.repository.DeliveryRepository;
import com.eedo.gdg.domain.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class OrderServiceImplTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryService deliveryService;

    @Test
    public void registerAndFind() throws Exception{

        //given

        //멤버
        Member new_member = Member.builder()
                .name("이도연")
                .address(Address.builder()
                        .city("성남시")
                        .street_address("가천대로")
                        .zipcode(11111).build())
                .build();

        Member member = memberRepository.save(new_member);

        // 배송지 정보
        Delivery new_delivery = Delivery.builder()
                .address(Address.builder()
                        .city(member.getAddress().getCity())
                        .street_address(member.getAddress().getStreet_address())
                        .zipcode(member.getAddress().getZipcode()).build())
                .build();
        Delivery delivery = deliveryRepository.save(new_delivery);


        OrderDto dto = OrderDto.builder()
                .member_id(member.getId())
                .address_id(delivery.getId())
                .build();

        //when
        Long order = orderService.order(dto);
        OrderDto result = orderService.find(order);

        //then
        Assertions.assertThat(result.getMember_name()).isEqualTo("이도연");
        log.info(result);
    }

    // 주문 완료
    @Test
    public void complete() throws Exception{

        //given
        //멤버
        Member new_member = Member.builder()
                .name("김도연")
                .address(Address.builder()
                        .city("성남시")
                        .street_address("장미로30")
                        .zipcode(12345).build())
                .build();

        Member member = memberRepository.save(new_member);

        // 배송지 정보
        Delivery new_delivery = Delivery.builder()
                .address(Address.builder()
                        .city(member.getAddress().getCity())
                        .street_address(member.getAddress().getStreet_address())
                        .zipcode(member.getAddress().getZipcode()).build())
                .build();
        Delivery delivery = deliveryRepository.save(new_delivery);


        OrderDto dto = OrderDto.builder()
                .member_id(member.getId())
                .address_id(delivery.getId())
                .build();

        Long order = orderService.order(dto);


        //when
        Order complete = orderService.complete(order);

        //then
        Optional<Delivery> byId = deliveryRepository.findById(delivery.getId());
        Delivery result_deli = byId.orElseThrow();

        // 오더 상태 변화
        Assertions.assertThat(complete.getStatus()).isEqualTo(OrderStatus.COMPLETE);
        log.info("order status: "+complete.getStatus());

        Assertions.assertThat(result_deli.getStatus()).isEqualTo(DeliveryStatus.DELIVERY);
        log.info("delivey status: "+result_deli.getStatus());

    }

    // 주문 취소
    @Test
    public void cancel() throws Exception{

        //given
        //멤버
        Member new_member = Member.builder()
                .name("박도연")
                .address(Address.builder()
                        .city("성남시")
                        .street_address("장미로12")
                        .zipcode(12345).build())
                .build();

        Member member = memberRepository.save(new_member);

        // 배송지 정보
        Delivery new_delivery = Delivery.builder()
                .address(Address.builder()
                        .city(member.getAddress().getCity())
                        .street_address(member.getAddress().getStreet_address())
                        .zipcode(member.getAddress().getZipcode()).build())
                .build();
        Delivery delivery = deliveryRepository.save(new_delivery);


        OrderDto dto = OrderDto.builder()
                .member_id(member.getId())
                .address_id(delivery.getId())
                .build();

        Long order = orderService.order(dto);

        //when
        Order cancel = orderService.cancel(order);

        //then
        Assertions.assertThat(cancel.getStatus()).isEqualTo(OrderStatus.CANCEL);

    }

    @Test
    public void updateAddress() throws Exception{

        //given

        //멤버
        Member new_member = Member.builder()
                .name("김도연")
                .address(Address.builder()
                        .city("성남시")
                        .street_address("장미로30")
                        .zipcode(12345).build())
                .build();

        Member member = memberRepository.save(new_member);

        // 배송지 정보
        Delivery new_delivery = Delivery.builder()
                .address(Address.builder()
                        .city(member.getAddress().getCity())
                        .street_address(member.getAddress().getStreet_address())
                        .zipcode(member.getAddress().getZipcode()).build())
                .build();
        Delivery delivery = deliveryRepository.save(new_delivery);


        OrderDto dto = OrderDto.builder()
                .member_id(member.getId())
                .address_id(delivery.getId())
                .build();

        Long order = orderService.order(dto);
        OrderDto first_result = orderService.find(order);
        log.info("기존 도시: " + first_result.getCity());


        // 새로운 배송지로 수정
        DeliveryDto update_delivery = DeliveryDto.builder()
                .id(delivery.getId())
                .city("수정된 도시")
                .street_address("수정된 도로")
                .zipcode(19999)
                .build();

        //when
        orderService.updateAddress(order,update_delivery);
        OrderDto orderDto = orderService.find(order);

        //then
        // 동일한 데이터인지 확인
        Assertions.assertThat(first_result.getId()).isEqualTo(order);
        Assertions.assertThat(orderDto.getId()).isEqualTo(order);

        Assertions.assertThat(orderDto.getCity()).isEqualTo("수정된 도시");
        log.info("수정된 도시: "+orderDto.getCity());
    }

    // 한 멤버가 여러개의 주문
    @Test
    public void multiOrder() throws Exception{
        //given

        //멤버
        Member new_member = Member.builder()
                .name("주문여러개")
                .address(Address.builder()
                        .city("성남시")
                        .street_address("장미로99")
                        .zipcode(12345).build())
                .build();

        Member member = memberRepository.save(new_member);

        // 배송지 정보1
        Delivery new_delivery1 = Delivery.builder()
                .address(Address.builder()
                        .city(member.getAddress().getCity())
                        .street_address(member.getAddress().getStreet_address())
                        .zipcode(member.getAddress().getZipcode()).build())
                .build();
        Delivery delivery1 = deliveryRepository.save(new_delivery1);

        // 배송지 정보2
        Delivery new_delivery2 = Delivery.builder()
                .address(Address.builder()
                        .city("강원도")
                        .street_address("평창군")
                        .zipcode(19402).build())
                .build();

        Delivery delivery2 = deliveryRepository.save(new_delivery2);


        OrderDto order1 = OrderDto.builder()
                .member_id(member.getId())
                .address_id(delivery1.getId())
                .build();

        OrderDto order2 = OrderDto.builder()
                .member_id(member.getId())
                .address_id(delivery2.getId())
                .build();


        //when
        Long order_1 = orderService.order(order1);
        Long order_2 = orderService.order(order2);

        OrderDto dto1 = orderService.find(order_1);
        OrderDto dto2 = orderService.find(order_2);


        //then
        Assertions.assertThat(dto1.getMember_id()).isEqualTo(member.getId());
        Assertions.assertThat(dto2.getMember_id()).isEqualTo(dto1.getMember_id());
        log.info(dto1.getMember_id());
    }
}