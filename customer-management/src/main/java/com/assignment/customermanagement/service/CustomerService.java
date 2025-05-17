package com.assignment.customermanagement.service;

import com.assignment.customermanagement.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customer);

    Customer getCustomerById(Long id);

    Page<Customer> getAllCustomers(Pageable pageable);
}
