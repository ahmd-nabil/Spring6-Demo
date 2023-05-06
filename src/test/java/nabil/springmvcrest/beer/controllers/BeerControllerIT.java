package nabil.springmvcrest.beer.controllers;

import nabil.springmvcrest.beer.entities.Beer;
import nabil.springmvcrest.beer.mappers.BeerMapper;
import nabil.springmvcrest.beer.model.BeerDTO;
import nabil.springmvcrest.beer.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Test
    void testGetAllBeers() {
        List<BeerDTO> dtos = beerController.getAllBeers();
        assertEquals(2413, dtos.size());
    }

    @Transactional
    @Rollback
    @Test
    void testGetAllBeersEmpty() {
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beerController.getAllBeers();
        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void testGetBeerById() {
        Beer dataBaseBeer = beerRepository.findAll().get(0);
        BeerDTO dto = beerController.getBeerById(dataBaseBeer.getId());
        assertNotNull(dto);
        assertThat(dto.getBeerName()).isEqualTo(dataBaseBeer.getBeerName());
        assertThat(dto.getId()).isEqualTo(dataBaseBeer.getId());
        assertThat(dto.getUpc()).isEqualTo(dataBaseBeer.getUpc());
        assertThat(dto.getVersion()).isEqualTo(dataBaseBeer.getVersion());
    }

    @Test
    void testGetBeerByIdException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });
    }

    @Transactional
    @Rollback
    @Test
    void testAddBeer() {
        BeerDTO beerDTO= BeerDTO.builder().beerName("newBear").quantityOnHand(99).build();
        ResponseEntity responseEntity = beerController.addBeer(beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertNotNull(responseEntity.getHeaders().get("Location"));

        UUID id = UUID.fromString(responseEntity.getHeaders().getLocation().getPath().split("/")[4]);

        Beer beer = beerRepository.findById(id).orElse(null);
        assertNotNull(beer);
        assertThat(beer.getBeerName()).isEqualTo("newBear");
    }

    @Transactional
    @Rollback
    @Test
    void testUpdateBeer() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        beerDTO.setBeerName("Updated");
        ResponseEntity responseEntity = beerController.updateBeer(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerDTO.getBeerName());
    }

    @Test
    void testUpdateBeerNotFound() {
        assertThrows(ResourceNotFoundException.class, ()-> beerController.updateBeer(UUID.randomUUID(), BeerDTO.builder().build()));
    }

    @Transactional
    @Rollback
    @Test
    void deleteById() {
        Beer beer = beerRepository.findAll().get(0);
        ResponseEntity responseEntity = beerController.deleteBeer(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(beerRepository.findById(beer.getId()).isEmpty());
    }

    @Test
    void deleteByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, ()->beerController.deleteBeer(UUID.randomUUID()));
    }
}