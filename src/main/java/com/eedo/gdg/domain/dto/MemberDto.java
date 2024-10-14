package com.eedo.gdg.domain.dto;

import com.eedo.gdg.domain.entity.Address;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MemberDto {

    private Long id;

    private String name;

    private String city;

    private String street_address;

    private int zipcode;


}
