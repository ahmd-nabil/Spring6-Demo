package nabil.springmvcrest.beer.repositories;

import nabil.springmvcrest.beer.entities.Beer;
import nabil.springmvcrest.beer.entities.BeerOrder;
import nabil.springmvcrest.beer.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureTestDatabase
class BeerOrderRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    Customer customer;
    Beer beer;
    @BeforeEach
    void setUp() {
        customer = customerRepository.findAll().get(0);
        beer = beerRepository.findAll().get(0);
    }

    @Test
    void testContextLoads() {
    }

    @Test
    @Transactional
    void testBeerOrders() {
        BeerOrder beerOrder = BeerOrder.builder().customer(customer).build();
        beerOrderRepository.save(beerOrder);
        BeerOrder savedBeerOrder = beerOrderRepository.findAll().get(0);
        System.out.println(savedBeerOrder);
    }

    @Test
    void testSaveBeerOrder() {
        BeerOrder beerOrder = BeerOrder.builder().customer(customer).build();
        beerOrderRepository.saveAndFlush(beerOrder);
        assertThat(beerOrderRepository.count()).isEqualTo(1);
    }

}