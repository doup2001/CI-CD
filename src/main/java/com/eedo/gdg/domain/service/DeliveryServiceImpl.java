package com.eedo.gdg.domain.service;

import com.eedo.gdg.domain.dto.DeliveryDto;
import com.eedo.gdg.domain.entity.Address;
import com.eedo.gdg.domain.entity.Delivery;
import com.eedo.gdg.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService{

    private final DeliveryRepository deliveryRepository;

    @Override
    public Long save(DeliveryDto dto) {
        Delivery delivery = dtoToEntity(dto);
        Delivery save = deliveryRepository.save(delivery);
        return save.getId();
    }

    @Override
    public DeliveryDto find(Long id) {
        Optional<Delivery> byId = deliveryRepository.findById(id);
        Delivery delivery = byId.orElseThrow();
        return entityToDTO(delivery);
    }

    //AOP 트랜잭션 처리
    @Override
    public Delivery update(DeliveryDto dto) {
        Optional<Delivery> byId = deliveryRepository.findById(dto.getId());
        Delivery delivery = byId.orElseThrow();

        Address new_Adrress = Address.builder()
                .city(dto.getCity())
                .street_address(dto.getStreet_address())
                .zipcode(dto.getZipcode())
                .build();

        delivery.update(new_Adrress);
        return delivery;
    }

    @Override
    public Delivery complete(Long id) {
        Optional<Delivery> byId = deliveryRepository.findById(id);
        Delivery delivery = byId.orElseThrow();
        delivery.complete();
        return delivery;
    }

    @Override
    public Delivery cancel(Long id) {
        Optional<Delivery> byId = deliveryRepository.findById(id);
        Delivery delivery = byId.orElseThrow();
        delivery.cancel();
        return delivery;
    }
}
