package nabil.springmvcrest.beer.repositories;

import nabil.springmvcrest.beer.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    List<Beer> findAllByBeerNameLike(String beerName);
}
