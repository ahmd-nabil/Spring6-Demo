package nabil.springmvcrest.beer.services;

import nabil.springmvcrest.beer.model.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface CustomerService {
    Page<CustomerDTO> findAll(Integer pageNumber, Integer pageSize);
    Optional<CustomerDTO> findById(UUID id);
    CustomerDTO add(CustomerDTO customerDTO);
    Optional<CustomerDTO> update(UUID id, CustomerDTO customerDTO);
    void deleteById(UUID id);
}
