package com.assignment.customermanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
//import org.apache.poi.ss.formula.functions.Address;
import com.assignment.customermanagement.entity.Address;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"addresses", "familyMembers"})
@EqualsAndHashCode(of = "id")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Date of birth is mandatory")
    private LocalDate dob;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "NIC is mandatory")
    private String nic;

    @ElementCollection
    @CollectionTable(name = "customer_addresses", joinColumns = @JoinColumn(name = "customer_id"))
    private List<Address> addresses = new ArrayList<>();


    @ElementCollection
    @CollectionTable(name = "customer_mobile_numbers", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "mobile_number")
    private List<String> mobileNumbers = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "customer_family_members",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "family_member_id")
    )
    private Set<Customer> familyMembers = new HashSet<>();
}
