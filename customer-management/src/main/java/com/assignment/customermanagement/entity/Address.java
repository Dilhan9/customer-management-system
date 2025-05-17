package com.assignment.customermanagement.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Address {

    @NotBlank(message = "Address Line 1 is mandatory")
    private String addressLine1;

    private String addressLine2;

    @NotBlank(message = "City code is mandatory")
    private String cityCode;

    @NotBlank(message = "Country code is mandatory")
    private String countryCode;
}
