package com.eedo.gdg.domain.service;

import com.eedo.gdg.domain.dto.DeliveryDto;
import com.eedo.gdg.domain.entity.Delivery;
import com.eedo.gdg.domain.entity.DeliveryStatus;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
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
    public void complete() throws Exception{

        DeliveryDto dto1 = DeliveryDto.builder()
                .city("성남시")
                .street_address("장미로55")
                .zipcode(13441)
                .build();

        Long id1 = deliveryService.save(dto1);

        DeliveryDto result1 = deliveryService.find(id1);


        log.info("result_1: " + result1.getStatus());

        //when
        Delivery complete = deliveryService.complete(id1);
        log.info("---------------------------------");

        //then
        org.assertj.core.api.Assertions.assertThat(complete.getStatus()).isEqualTo(DeliveryStatus.DELIVERY);

        log.info("result_1: " + complete.getStatus());

    }

    @Test
    public void Cancel() throws Exception{

        DeliveryDto dto2 = DeliveryDto.builder()
                .city("서울시")
                .street_address("서울로11")
                .zipcode(13441)
                .build();

        Long id2= deliveryService.save(dto2);

        DeliveryDto result2 = deliveryService.find(id2);

        log.info("result_2: "+ result2.getStatus());

        //when
        Delivery cancel = deliveryService.cancel(id2);
        log.info("---------------------------------");

        //then
        org.assertj.core.api.Assertions.assertThat(cancel.getStatus()).isEqualTo(DeliveryStatus.CANCEL);

        log.info("result_2: " + cancel.getStatus());

    }

    @Test
    public void update() throws Exception{

        //given
        DeliveryDto dto = DeliveryDto.builder()
                .city("성남시")
                .street_address("장미로55")
                .zipcode(13441)
                .build();

        Long save = deliveryService.save(dto);

        DeliveryDto update_dto = DeliveryDto.builder()
                .id(save)
                .city("수정된")
                .street_address("수정된도로")
                .zipcode(99999)
                .build();
        //when
        Delivery update = deliveryService.update(update_dto);

        //then
        Assertions.assertThat(update.getId()).isEqualTo(save);
        Assertions.assertThat(update.getAddress().getCity()).isEqualTo("수정된");
        Assertions.assertThat(update.getAddress().getStreet_address()).isEqualTo("수정된도로");
        log.info(update.getId());
        log.info(update.getAddress().getCity());
        log.info(update.getAddress().getStreet_address());
        log.info(update.getAddress().getZipcode());
    }

}