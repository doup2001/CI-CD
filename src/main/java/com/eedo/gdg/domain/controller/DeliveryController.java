package com.eedo.gdg.domain.controller;

import com.eedo.gdg.domain.dto.DeliveryDto;
import com.eedo.gdg.domain.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping()
    public String hi() {
        return "Delivery Hello";
    }

    @PostMapping()
    public Map<String, Object> register(DeliveryDto dto) {
        Long save = deliveryService.save(dto);
        return Map.of("Result", save);
    }

    @GetMapping("/find/{id}")
    public Map<String, Object> register(@PathVariable Long id) {
        DeliveryDto dto = deliveryService.find(id);
        return Map.of("Result", dto);
    }


}
