package com.assignment.customermanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Customer {
    @Id @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate dateOfBirth;

    @Column(unique = true)
    private String nic;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MobileNumber> mobileNumbers;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;

    @ManyToMany
    private List<Customer> familyMembers;
}

