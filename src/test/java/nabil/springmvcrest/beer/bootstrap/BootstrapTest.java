package nabil.springmvcrest.beer.bootstrap;

import nabil.springmvcrest.beer.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class BootstrapTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testRun() throws Exception {
        assertThat(beerRepository.findAll().size()).isEqualTo(2413);
    }
}