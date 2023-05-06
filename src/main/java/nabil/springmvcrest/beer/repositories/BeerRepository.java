package nabil.springmvcrest.beer.repositories;

import nabil.springmvcrest.beer.entities.Beer;
import nabil.springmvcrest.beer.model.BeerStyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    List<Beer> findAllByBeerNameIsLikeIgnoreCase(String beerName);
    List<Beer> findAllByBeerStyle(BeerStyle beerStyle);
}
