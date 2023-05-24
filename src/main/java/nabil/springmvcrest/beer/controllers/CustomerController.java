package nabil.springmvcrest.beer.controllers;

import lombok.RequiredArgsConstructor;
import nabil.springmvcrest.beer.model.CustomerDTO;
import nabil.springmvcrest.beer.services.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    public final static String CUSTOMER_API = "/api/v1/customers";
    public final static String CUSTOMER_API_ID = CUSTOMER_API + "/{id}";

    @GetMapping(CUSTOMER_API)
    public List<CustomerDTO> getAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping(CUSTOMER_API_ID)
    public CustomerDTO getCustomer(@PathVariable(name = "id") UUID id) {
        return customerService.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @PostMapping(CUSTOMER_API)
    public ResponseEntity saveCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO saveCustomerDTO = customerService.add(customerDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", saveCustomerDTO.getId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_API_ID)
    public ResponseEntity updateCustomer(@PathVariable("id") UUID id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.update(id, customerDTO).orElseThrow(ResourceNotFoundException::new);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", CUSTOMER_API + "/" +updatedCustomer.getId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.NO_CONTENT);
    }
}
