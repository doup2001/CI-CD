package com.eedo.gdg.domain.service;


import com.eedo.gdg.domain.dto.DeliveryDto;
import com.eedo.gdg.domain.dto.MemberDto;
import com.eedo.gdg.domain.dto.OrderDto;
import com.eedo.gdg.domain.entity.Address;
import com.eedo.gdg.domain.entity.Delivery;
import com.eedo.gdg.domain.entity.Member;
import com.eedo.gdg.domain.entity.Order;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

public interface OrderService {

    // 주문 생성
    Long order(OrderDto dto);

    // 주문 조회
    OrderDto find(Long id);

    // 주문 완료
    Order complete(Long id);

    // 주문 취소
    Order cancel(Long id);

    // 주문 배송지 변경
    Order updateAddress(Long id, DeliveryDto dto);

    // ModelMapper 대신 메서드 추가
    default OrderDto entityToDTO(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .member_id(order.getMember().getId())
                .member_name(order.getMember().getName())
                .address_id(order.getDelivery().getId())
                .city(order.getDelivery().getAddress().getCity())
                .street_address(order.getDelivery().getAddress().getStreet_address())
                .zipcode(order.getDelivery().getAddress().getZipcode())
                .build();
    }
}

