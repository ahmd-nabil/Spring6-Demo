package nabil.springmvcrest.beer.mappers;

import nabil.springmvcrest.beer.entities.Customer;
import nabil.springmvcrest.beer.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToCustomerDto(Customer customer);
}
