package com.assignment.customermanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "countries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "code")
public class Country {

    @Id
    @NotBlank(message = "Country code is mandatory")
    private String code;

    @NotBlank(message = "Country name is mandatory")
    private String name;
}
