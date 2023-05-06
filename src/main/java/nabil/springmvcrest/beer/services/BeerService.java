package nabil.springmvcrest.beer.services;

import nabil.springmvcrest.beer.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<BeerDTO> findAll(String beerName);
    Optional<BeerDTO> findBeerById(UUID id);
    BeerDTO addNewBeer(BeerDTO beer);
    Optional<BeerDTO> updateBeer(UUID id, BeerDTO newBeer);

    boolean delete(UUID id);
}
