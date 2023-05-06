package nabil.springmvcrest.beer.services;

import lombok.RequiredArgsConstructor;
import nabil.springmvcrest.beer.controllers.ResourceNotFoundException;
import nabil.springmvcrest.beer.entities.Beer;
import nabil.springmvcrest.beer.mappers.BeerMapper;
import nabil.springmvcrest.beer.model.BeerDTO;
import nabil.springmvcrest.beer.model.BeerStyle;
import nabil.springmvcrest.beer.repositories.BeerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
public class BeerServiceJPA implements BeerService{
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    @Override
    public List<BeerDTO> findAll(String beerName, BeerStyle beerStyle) {
        List<Beer> result;
        if(StringUtils.hasText(beerName) && beerStyle == null) {
            result = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%");
        } else if(!StringUtils.hasText(beerName) && beerStyle != null) {
            result = beerRepository.findAllByBeerStyle(beerStyle);
        } else {
            result = beerRepository.findAll();
        }
        return result.stream().map(beerMapper::beerToBeerDto).collect(Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> findBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id).orElse(null)));
    }

    @Override
    public BeerDTO addNewBeer(BeerDTO beer) {
        Beer savedBeer = beerRepository.save(beerMapper.beerDtoToBeer(beer));
        return beerMapper.beerToBeerDto(savedBeer);
    }

    @Override
    public Optional<BeerDTO> updateBeer(UUID id, BeerDTO newBeer) {
        Beer foundBeer = beerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        foundBeer.setBeerName(newBeer.getBeerName());
        foundBeer.setBeerStyle(newBeer.getBeerStyle());
        foundBeer.setPrice(newBeer.getPrice());
        foundBeer.setUpc(newBeer.getUpc());
        foundBeer.setQuantityOnHand(newBeer.getQuantityOnHand());
        beerRepository.save(foundBeer);
        return Optional.of(beerMapper.beerToBeerDto(foundBeer));
    }

    @Override
    public boolean delete(UUID id) {
        if(beerRepository.existsById(id)) {
            beerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
