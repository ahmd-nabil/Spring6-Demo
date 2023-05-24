package nabil.springmvcrest.beer.services;

import lombok.RequiredArgsConstructor;
import nabil.springmvcrest.beer.mappers.CustomerMapper;
import nabil.springmvcrest.beer.model.CustomerDTO;
import nabil.springmvcrest.beer.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService{

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final static Integer DEFAULT_PAGE_NUMBER = 1;
    private final static Integer DEFAULT_PAGE_SIZE = 50;
    private final static Integer MAX_PAGE_SIZE = 100;
    @Override
    public Page<CustomerDTO> findAll(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = getPageRequest(pageNumber, pageSize);
        return customerRepository.findAll(pageRequest).map(customerMapper::customerToCustomerDto);
//        return customerRepository.findAll().stream().map(customerMapper::customerToCustomerDto).toList();
    }

    private PageRequest getPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber = DEFAULT_PAGE_NUMBER;
        int queryPageSize = DEFAULT_PAGE_SIZE;
        if(pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber;
        }

        if(pageSize != null && pageSize > 0) {
            queryPageSize = pageSize;
            if(pageSize > MAX_PAGE_SIZE)
                queryPageSize = MAX_PAGE_SIZE;
        }
        return PageRequest.of(queryPageNumber - 1 , queryPageSize);
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
        if(!customerRepository.existsById(id))
            return Optional.ofNullable(null);
        return Optional.of(customerMapper.customerToCustomerDto(customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO))));
    }

    @Override
    public void deleteById(UUID id) {
        if(!customerRepository.existsById(id)) {
            return;
        }
        customerRepository.deleteById(id);
    }
}
