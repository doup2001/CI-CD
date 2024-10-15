package com.eedo.gdg.domain.dto;

import com.eedo.gdg.domain.entity.DeliveryStatus;
import com.eedo.gdg.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryDto {
    private Long id;

    private String city;

    private String street_address;

    private int zipcode;

    private String status;

}
