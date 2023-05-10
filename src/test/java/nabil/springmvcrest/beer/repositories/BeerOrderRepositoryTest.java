package nabil.springmvcrest.beer.repositories;

import nabil.springmvcrest.beer.entities.BeerOrder;
import nabil.springmvcrest.beer.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class BeerOrderRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    Customer customer;
    @BeforeEach
    void setUp() {
        customer = customerRepository.findAll().get(0);
    }

    @Test
    void testContextLoads() {
        System.out.println(customerRepository.count());
        System.out.println(beerRepository.count());
        System.out.println(beerOrderRepository.count());
    }

    @Transactional
    @Test
    void testSaveBeerOrder() {
        BeerOrder beerOrder = BeerOrder.builder().customer(customer).build();
        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);
        System.out.println(customer.getBeerOrders());
    }

}