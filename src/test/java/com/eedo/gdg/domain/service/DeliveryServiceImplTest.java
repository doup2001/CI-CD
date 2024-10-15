package com.eedo.gdg.domain.service;

import com.eedo.gdg.domain.dto.DeliveryDto;
import com.eedo.gdg.domain.entity.Delivery;
import com.eedo.gdg.domain.entity.DeliveryStatus;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Log4j2
@Transactional
@Rollback(value = false)
class DeliveryServiceImplTest {

    @Autowired
    private DeliveryService deliveryService;

    @Test
    public void registerAndFind() throws Exception{
        //given
        DeliveryDto dto = DeliveryDto.builder()
                .city("성남시")
                .street_address("장미로55")
                .zipcode(13441)
                .build();

        //when
        Long saved = deliveryService.save(dto);
        DeliveryDto deliveryDto = deliveryService.find(saved);

        //then
        assertThat(deliveryDto.getCity()).isEqualTo("성남시");
        log.info(deliveryDto);
    }

    @Test
    public void completeAndCancel() throws Exception{

        DeliveryDto dto1 = DeliveryDto.builder()
                .city("성남시")
                .street_address("장미로55")
                .zipcode(13441)
                .build();

        DeliveryDto dto2 = DeliveryDto.builder()
                .city("서울시")
                .street_address("서울로11")
                .zipcode(13441)
                .build();

        Long id1 = deliveryService.save(dto1);
        Long id2= deliveryService.save(dto2);

        DeliveryDto result1 = deliveryService.find(id1);
        DeliveryDto result2 = deliveryService.find(id2);

        log.info("result_1: " + result1.getStatus());
        log.info("result_2: "+ result2.getStatus());

        //when
        Delivery complete = deliveryService.complete(id1);
        Delivery cancel = deliveryService.cancel(id2);
        log.info("---------------------------------");

        //then
        org.assertj.core.api.Assertions.assertThat(complete.getStatus()).isEqualTo(DeliveryStatus.DELIVERY);
        org.assertj.core.api.Assertions.assertThat(cancel.getStatus()).isEqualTo(DeliveryStatus.CANCEL);

        log.info("result_1: " + complete.getStatus());
        log.info("result_2: " + cancel.getStatus());

    }

}