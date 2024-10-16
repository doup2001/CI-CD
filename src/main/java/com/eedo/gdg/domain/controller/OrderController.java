package com.eedo.gdg.domain.controller;

import com.eedo.gdg.domain.dto.DeliveryDto;
import com.eedo.gdg.domain.dto.OrderDto;
import com.eedo.gdg.domain.entity.Order;
import com.eedo.gdg.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping()
    public String hi() {
        return " Order Hello";
    }

    @PostMapping()
    public Map<String, Object> order(OrderDto dto) {
        Long order = orderService.order(dto);
        return Map.of("Result", order);
    }

    @GetMapping("/find/{id}")
    public Map<String, Object> find(@PathVariable Long id) {
        OrderDto dto = orderService.find(id);
        return Map.of("Result", dto);
    }

    @PutMapping("/update/address/{id}")
    public Map<String, Object> update_addr(@PathVariable Long id, DeliveryDto new_address_dto) {

        Order order = orderService.updateAddress(id,new_address_dto);
        OrderDto dto = orderService.entityToDTO(order);

        return Map.of("Result", dto);
    }
}
