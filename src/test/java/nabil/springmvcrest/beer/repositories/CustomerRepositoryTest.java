package nabil.springmvcrest.beer.repositories;

import nabil.springmvcrest.beer.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    Customer customer;

    @BeforeEach
    void setUp() {
         customer = Customer.builder().firstName("Ahmed").lastName("Nabil").build();
    }

    @Test
    void testSave() {
        Customer savedCustomer = customerRepository.saveAndFlush(customer); // CreateTimeStamp notation will not work until flushed.
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getCreatedDate()).isNotNull();
        assertThat(savedCustomer.getUpdatedDate()).isNotNull();
        assertThat(savedCustomer.getVersion()).isEqualTo(0);
        assertThat(savedCustomer.getFirstName()).isEqualTo("Ahmed");
    }
}