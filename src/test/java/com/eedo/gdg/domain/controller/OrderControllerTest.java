package com.eedo.gdg.domain.controller;

import com.eedo.gdg.domain.dto.OrderDto;
import com.eedo.gdg.domain.entity.Address;
import com.eedo.gdg.domain.entity.Delivery;
import com.eedo.gdg.domain.entity.Member;
import com.eedo.gdg.domain.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }


    @Test
    @DisplayName("주문하기")
    void Order() throws Exception {

        // given


        Member member = Member.builder()
                .id(3L)
                .name("mockito테스트")
                .address(Address.builder()
                        .city("성남시")
                        .street_address("장미로55")
                        .zipcode(11111)
                        .build())
                .build();

        Delivery.builder()
                .id(4L)
                .address(Address.builder()
                        .city("서울시")
                        .street_address("서울로11")
                        .zipcode(33333)
                        .build())
                .build();

        OrderDto orderdto = OrderDto.builder()
                .id(1L)
                .member_id(3L)
                .address_id(4L)
                .build();

        // when
        ResultActions actions = mockMvc.perform(post("/api/order/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(orderdto)))
                //then
                .andExpect(status().isOk())
                .andDo(print());

    }
}