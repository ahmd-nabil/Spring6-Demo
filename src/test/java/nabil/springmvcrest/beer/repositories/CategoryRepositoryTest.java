package nabil.springmvcrest.beer.repositories;

import nabil.springmvcrest.beer.entities.Beer;
import nabil.springmvcrest.beer.entities.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author Ahmed Nabil
 */

@SpringBootTest
class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BeerRepository beerRepository;

    Beer savedBeer;

    @BeforeEach
    void setUp() {
        savedBeer = beerRepository.findAll().get(0);
    }

    @Test
    @Transactional
    void testAddBeerToBeerSetCheckSync() {
        Category savedCategory = categoryRepository.save(Category.builder().description("Desc1").build());
        savedCategory.addBeer(savedBeer);
        Beer fetchedBeer = beerRepository.findAll().get(0);
        assertThat(fetchedBeer.getCategories().size()).isEqualTo(1);
        assertThat(fetchedBeer.getCategories().contains(savedCategory)).isTrue();
    }

    @Test
    @Transactional
    void testRemoveBeerFromBeerSetCheckSync() {
        // given
        Category savedCategory = categoryRepository.save(Category.builder().description("Desc1").build());
        savedCategory.addBeer(savedBeer);
        assertThat(savedBeer.getCategories().size()).isEqualTo(1);
        assertThat(savedBeer.getCategories().contains(savedCategory)).isTrue();
        // when
        savedCategory.removeBeer(savedBeer);
        // then
        assertThat(savedBeer.getCategories().size()).isEqualTo(0);
        assertThat(savedCategory.getBeers().size()).isEqualTo(0);
    }

}