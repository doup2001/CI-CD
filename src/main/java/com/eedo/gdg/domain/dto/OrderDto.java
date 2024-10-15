package com.eedo.gdg.domain.dto;

import com.eedo.gdg.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private Long id;

    private Long member_id;

    private String member_name;

    private Long address_id;

    private String city;

    private String street_address;

    private int zipcode;
}
