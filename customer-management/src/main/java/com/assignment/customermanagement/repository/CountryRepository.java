package com.assignment.customermanagement.repository;

import com.assignment.customermanagement.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    // Additional query methods can be added here if needed
}
