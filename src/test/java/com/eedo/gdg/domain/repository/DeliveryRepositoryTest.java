package com.eedo.gdg.domain.repository;

import com.eedo.gdg.domain.entity.Address;
import com.eedo.gdg.domain.entity.Delivery;
import com.eedo.gdg.domain.entity.DeliveryStatus;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Log4j2
class DeliveryRepositoryTest {

    @Autowired
    private DeliveryRepository deliveryRepository;

    // delivery case생성
    @Test
    public void generate() throws Exception{
        //given
        Delivery testDelivery = Delivery.builder()
                .address(Address.builder()
                        .city("성남시")
                        .street_address("장미로55")
                        .zipcode(13441).build())
                .status(DeliveryStatus.Delivery)
                .build();

        //when
        Delivery delivery = deliveryRepository.save(testDelivery);

        //then
        Assertions.assertThat(delivery.getAddress().getCity()).isEqualTo("성남시");
    }

    // 삭제 테스트 case
    @Test
    public void delete() throws Exception{
        //given
        Delivery testDelivery = Delivery.builder()
                .address(Address.builder()
                        .city("성남시")
                        .street_address("장미로55")
                        .zipcode(13441).build())
                .status(DeliveryStatus.Delivery)
                .build();

        Delivery save = deliveryRepository.save(testDelivery);
        Long id = save.getId();

        Optional<Delivery> byId = deliveryRepository.findById(id);
        Delivery delivery = byId.orElseThrow();

        //when
        deliveryRepository.delete(delivery);

        //then
        Throwable exception = assertThrows(NoSuchElementException.class, () -> {
            deliveryRepository.findById(id).orElseThrow();
        });

        assertEquals("No value present", exception.getMessage());
        log.info(exception.getMessage());
    }


}