package nabil.springmvcrest.beer.controllers;

import nabil.springmvcrest.beer.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {
    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void getCustomerByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, ()->customerController.getCustomer(UUID.randomUUID()));
    }
}