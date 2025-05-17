package com.assignment.customermanagement.service;

import com.assignment.customermanagement.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(CustomerDto dto);
    Customer updateCustomer(Long id, CustomerDto dto);
    Customer getCustomer(Long id);
    List<Customer> getAllCustomers();
}

