package nabil.springmvcrest.beer.services;

import nabil.springmvcrest.beer.model.BeerDTO;
import nabil.springmvcrest.beer.model.BeerStyle;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Page<BeerDTO> findAll(String beerName, BeerStyle beerStyle, Integer pageNumber, Integer pageSize);
    Optional<BeerDTO> findBeerById(UUID id);
    BeerDTO addNewBeer(BeerDTO beer);
    Optional<BeerDTO> updateBeer(UUID id, BeerDTO newBeer);

    boolean delete(UUID id);
}
