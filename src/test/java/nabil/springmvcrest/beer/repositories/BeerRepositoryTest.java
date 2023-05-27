package nabil.springmvcrest.beer.repositories;

import jakarta.validation.ConstraintViolationException;
import nabil.springmvcrest.beer.entities.Beer;
import nabil.springmvcrest.beer.entities.Category;
import nabil.springmvcrest.beer.model.BeerStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CategoryRepository categoryRepository;
    Beer beer;

    @BeforeEach
    void setUp() {
        beer = Beer.builder()
                .beerName("My Beer")
                .beerStyle(BeerStyle.GOSE)
                .upc("UPC")
                .price(new BigDecimal("10.99"))
                .build();
    }

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(beer);
        beerRepository.flush();
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
        assertEquals(beer.getBeerName(), savedBeer.getBeerName());
    }

    @Test
    void testSaveBeerLongName() {
        assertThrows(ConstraintViolationException.class, () -> {
            beer.setBeerName("Beer+50charsBeer+50charsBeer+50charsBeer+50charsBeer+50charsBeer+50charsBeer+50charsBeer+50charsBeer+50charsBeer+50charsBeer+50charsBeer+50charsBeer+50charsBeer+50charsBeer+50chars");
            Beer savedBeer = beerRepository.save(beer);
            beerRepository.flush();
        });
    }

    @Test
    @Transactional
    void testSaveBeerAndCategorySync() {
        Beer savedBeer = beerRepository.save(beer);
        Category savedCategory = categoryRepository.save(Category.builder().description("cat1").build());
        savedBeer.addCategory(savedCategory);
        assertThat(savedCategory.getBeers().size()).isEqualTo(1);
        assertThat(savedCategory.getBeers().contains(savedBeer)).isTrue();
    }


    @Test
    @Transactional
    void testRemoveCategorySync() {
        // given
        Category savedCat = categoryRepository.save(Category.builder().description("CAT!").build());
        Beer savedBeer = beerRepository.save(beer);
        beer.addCategory(savedCat);
        beerRepository.flush();
        assertThat(savedBeer.getCategories().size()).isEqualTo(1);
        assertThat(savedCat.getBeers().size()).isEqualTo(1);
        //when
        savedBeer.removeCategory(savedCat);
        assertThat(savedCat.getBeers().size()).isEqualTo(0);
    }
}