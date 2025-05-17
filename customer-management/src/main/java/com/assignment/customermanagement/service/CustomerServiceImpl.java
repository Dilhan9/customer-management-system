package com.assignment.customermanagement.service;

import com.assignment.customermanagement.entity.Customer;
import com.assignment.customermanagement.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
//import jakarta.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Optional<Customer> existing = customerRepository.findByNic(customer.getNic());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Customer with NIC " + customer.getNic() + " already exists.");
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + id));

        if (!existingCustomer.getNic().equals(customer.getNic())) {
            Optional<Customer> nicCheck = customerRepository.findByNic(customer.getNic());
            if (nicCheck.isPresent()) {
                throw new IllegalArgumentException("Customer with NIC " + customer.getNic() + " already exists.");
            }
            existingCustomer.setNic(customer.getNic());
        }

        existingCustomer.setName(customer.getName());
        existingCustomer.setDob(customer.getDob());
        existingCustomer.setMobileNumbers(customer.getMobileNumbers());
        existingCustomer.setAddresses(customer.getAddresses());
        existingCustomer.setFamilyMembers(customer.getFamilyMembers());

        return customerRepository.save(existingCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }
}

