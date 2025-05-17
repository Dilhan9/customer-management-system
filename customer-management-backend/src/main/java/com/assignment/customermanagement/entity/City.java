package com.assignment.customermanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "cities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "code")
public class City {

    @Id
    @NotBlank(message = "City code is mandatory")
    private String code;

    @NotBlank(message = "City name is mandatory")
    private String name;
}
