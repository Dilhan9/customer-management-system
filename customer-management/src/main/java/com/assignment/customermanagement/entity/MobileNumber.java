package com.assignment.customermanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;

@Entity
public class MobileNumber {
    @Id
    @GeneratedValue
    private Long id;

    private String number;
}

