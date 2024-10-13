package com.eedo.gdg.domain.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String city;

    private String street_address;

    private int zipcode;

}
