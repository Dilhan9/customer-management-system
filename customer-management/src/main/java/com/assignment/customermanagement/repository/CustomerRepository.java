package com.assignment.customermanagement.repository;

import com.assignment.customermanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Find customer by NIC (unique identifier)
    Optional<Customer> findByNic(String nic);
    boolean existsByNic(String nic);

    // You can add more custom queries if needed
}



