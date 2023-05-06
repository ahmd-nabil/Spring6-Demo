package nabil.springmvcrest.beer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nabil.springmvcrest.beer.model.BeerDTO;
import nabil.springmvcrest.beer.services.BeerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {
    private final BeerService beerService;

    public static final String BEER_API = "/api/v1/beers";
    public static final String BEER_API_ID = BEER_API + "/{id}";
    @GetMapping(BEER_API)
    public List<BeerDTO> getAllBeers(@RequestParam(name = "beerName", required = false) String beerName) {
        return beerService.findAll(beerName);
    }

    @GetMapping(BEER_API_ID)
    public BeerDTO getBeerById(@PathVariable("id") @org.hibernate.validator.constraints.UUID UUID id) {
        return beerService.findBeerById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @PostMapping(BEER_API)
    public ResponseEntity addBeer(@Validated @RequestBody BeerDTO beer) {
        beer = beerService.addNewBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_API + "/" + beer.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(BEER_API_ID)
    public ResponseEntity updateBeer(@PathVariable("id") UUID id, @RequestBody BeerDTO beer) {
        beer = beerService.updateBeer(id, beer).orElseThrow(ResourceNotFoundException::new);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_API + "/" + beer.getId().toString());
        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_API_ID)
    public ResponseEntity deleteBeer(@PathVariable("id") UUID id) {
        if(beerService.delete(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        throw new ResourceNotFoundException();
    }

    @DeleteMapping(BEER_API)
    public ResponseEntity idInBodyDelete(@RequestBody String id) {
        beerService.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
