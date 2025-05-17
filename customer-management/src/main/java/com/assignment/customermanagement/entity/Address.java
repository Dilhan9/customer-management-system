package com.assignment.customermanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;

@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String country;
}

