package nabil.springmvcrest.beer.services;

import lombok.RequiredArgsConstructor;
import nabil.springmvcrest.beer.mappers.CustomerMapper;
import nabil.springmvcrest.beer.model.CustomerDTO;
import nabil.springmvcrest.beer.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService{

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    @Override
    public List<CustomerDTO> findAll() {
        return customerRepository.findAll().stream().map(customerMapper::customerToCustomerDto).toList();
    }
    @Override
    public Optional<CustomerDTO> findById(UUID id) {
        return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository.findById(id).orElse(null)));
    }

    @Override
    public CustomerDTO add(CustomerDTO customerDTO) {
        return customerMapper.customerToCustomerDto(customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO)));
    }

    @Override
    public Optional<CustomerDTO> update(UUID id, CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }
}
