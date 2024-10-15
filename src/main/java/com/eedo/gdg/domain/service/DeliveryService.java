package com.eedo.gdg.domain.service;

import com.eedo.gdg.domain.dto.DeliveryDto;
import com.eedo.gdg.domain.dto.MemberDto;
import com.eedo.gdg.domain.entity.Address;
import com.eedo.gdg.domain.entity.Delivery;
import com.eedo.gdg.domain.entity.Member;

public interface DeliveryService {

    // 새로운 배송 정보 저장
    Long save(DeliveryDto dto);

    // 배송 정보 확인
    DeliveryDto find(Long id);

    // 배송지 수정
    Delivery update(DeliveryDto dto);

    // 배송 완료
    Delivery complete(Long id);

    // 배송 취소
    Delivery cancel(Long id);

    // ModelMapper 대신 메서드 추가
    default DeliveryDto entityToDTO(Delivery delivery) {
        return DeliveryDto.builder()
                .id(delivery.getId())
                .city(delivery.getAddress().getCity())
                .street_address(delivery.getAddress().getStreet_address())
                .zipcode(delivery.getAddress().getZipcode())
                .status(delivery.getStatus().toString())
                .build();
    }

    default Delivery dtoToEntity(DeliveryDto dto) {
        return Delivery.builder()
                .address(Address.builder()
                        .city(dto.getCity())
                        .street_address(dto.getStreet_address())
                        .zipcode(dto.getZipcode()).build())
                .build();
    }

}
