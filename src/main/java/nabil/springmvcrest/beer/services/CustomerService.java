package nabil.springmvcrest.beer.services;

import nabil.springmvcrest.beer.model.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface CustomerService {
    List<CustomerDTO> findAll();
    Optional<CustomerDTO> findById(UUID id);
    CustomerDTO add(CustomerDTO customerDTO);
    Optional<CustomerDTO> update(UUID id, CustomerDTO customerDTO);
    void deleteById(UUID id);
}
