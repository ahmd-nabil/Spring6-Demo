package nabil.springmvcrest.beer.bootstrap;

import nabil.springmvcrest.beer.repositories.BeerRepository;
import nabil.springmvcrest.beer.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class BootstrapTest {
    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testBeersPopulated()  {
        assertThat(beerRepository.findAll().size()).isEqualTo(2413);
    }

    @Test
    void testCustomersPopulated()  {
        assertThat(customerRepository.findAll().size()).isEqualTo(2);
    }
}