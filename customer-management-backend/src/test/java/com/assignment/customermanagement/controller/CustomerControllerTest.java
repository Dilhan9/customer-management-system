package com.assignment.customermanagement.controller;

import com.assignment.customermanagement.entity.Customer;
import com.assignment.customermanagement.service.CustomerBulkService;
import com.assignment.customermanagement.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerBulkService customerBulkService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateCustomer_success() throws Exception {
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        customer.setNic("987654321V");
        customer.setDob(LocalDate.of(1985, 5, 15));

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setName(customer.getName());
        savedCustomer.setNic(customer.getNic());
        savedCustomer.setDob(customer.getDob());

        Mockito.when(customerService.createCustomer(any(Customer.class))).thenReturn(savedCustomer);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jane Smith"))
                .andExpect(jsonPath("$.nic").value("987654321V"));
    }

    @Test
    void testUpdateCustomer_success() throws Exception {
        Customer customer = new Customer();
        customer.setName("Updated Name");
        customer.setNic("123456789V");
        customer.setDob(LocalDate.of(1990, 1, 1));

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1L);
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setNic(customer.getNic());
        updatedCustomer.setDob(customer.getDob());

        Mockito.when(customerService.updateCustomer(eq(1L), any(Customer.class))).thenReturn(updatedCustomer);

        mockMvc.perform(put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.nic").value("123456789V"));
    }

    @Test
    void testGetCustomerById_found() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setNic("123456789V");
        customer.setDob(LocalDate.of(1990, 1, 1));

        Mockito.when(customerService.getCustomerById(1L)).thenReturn(customer);

        mockMvc.perform(get("/api/customers/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.nic").value("123456789V"));
    }

    @Test
    void testGetAllCustomers_success() throws Exception {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("John Doe");
        customer1.setNic("123456789V");
        customer1.setDob(LocalDate.of(1990, 1, 1));

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Jane Smith");
        customer2.setNic("987654321V");
        customer2.setDob(LocalDate.of(1985, 5, 15));

        List<Customer> customers = List.of(customer1, customer2);
        Page<Customer> page = new PageImpl<>(customers, PageRequest.of(0, 10), customers.size());

        Mockito.when(customerService.getAllCustomers(any())).thenReturn(page);

        mockMvc.perform(get("/api/customers?page=0&size=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2));
    }

    @Test
    void testUploadCustomersExcel_success() throws Exception {
        Mockito.when(customerBulkService.processExcel(any(MultipartFile.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        mockMvc.perform(multipart("/api/customers/upload")
                        .file("file", "dummy content".getBytes()))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Bulk upload started. You will be notified upon completion."));
    }
}
