package com.assignment.customermanagement.service;

import com.assignment.customermanagement.entity.Customer;
import com.assignment.customermanagement.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setNic("123456789V");
        customer.setDob(LocalDate.of(1990, 1, 1));
        // Set other fields as needed
    }

    @Test
    void createCustomer_whenNicExists_shouldThrowException() {
        when(customerRepository.findByNic(customer.getNic())).thenReturn(Optional.of(customer));

        assertThatThrownBy(() -> customerService.createCustomer(customer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");

        verify(customerRepository, never()).save(any());
    }

    @Test
    void createCustomer_whenNicNotExists_shouldSaveCustomer() {
        when(customerRepository.findByNic(customer.getNic())).thenReturn(Optional.empty());
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer saved = customerService.createCustomer(customer);

        assertThat(saved).isNotNull();
        verify(customerRepository).save(customer);
    }

    @Test
    void getCustomerById_whenExists_shouldReturnCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer found = customerService.getCustomerById(1L);

        assertThat(found).isEqualTo(customer);
    }

    @Test
    void getCustomerById_whenNotExists_shouldThrowException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.getCustomerById(1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getAllCustomers_shouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> page = new PageImpl<>(List.of(customer));
        when(customerRepository.findAll(pageable)).thenReturn(page);

        Page<Customer> result = customerService.getAllCustomers(pageable);

        assertThat(result.getContent()).hasSize(1);
    }
    
}

