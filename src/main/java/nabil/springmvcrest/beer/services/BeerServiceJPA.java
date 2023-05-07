package nabil.springmvcrest.beer.services;

import lombok.RequiredArgsConstructor;
import nabil.springmvcrest.beer.controllers.ResourceNotFoundException;
import nabil.springmvcrest.beer.entities.Beer;
import nabil.springmvcrest.beer.mappers.BeerMapper;
import nabil.springmvcrest.beer.model.BeerDTO;
import nabil.springmvcrest.beer.model.BeerStyle;
import nabil.springmvcrest.beer.repositories.BeerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Primary
public class BeerServiceJPA implements BeerService{
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 3000;
    @Override
    public Page<BeerDTO> findAll(String beerName, BeerStyle beerStyle, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        return findAllUtil(beerName, beerStyle, pageRequest);
    }

    private Page<BeerDTO> findAllUtil(String beerName, BeerStyle beerStyle, PageRequest pageRequest) {
        Page<Beer> resultPage;
        if(StringUtils.hasText(beerName)) {
            beerName = "%" + beerName + "%";
            if(beerStyle == null) {
                resultPage = beerRepository.findAllByBeerNameIsLikeIgnoreCase(beerName, pageRequest);
            } else {
                resultPage = beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(beerName , beerStyle, pageRequest);
            }
        } else if(beerStyle != null) {
            resultPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            resultPage = beerRepository.findAll(pageRequest);
        }
        return resultPage.map(beerMapper::beerToBeerDto);
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        Integer queryPageNumber= BeerServiceJPA.DEFAULT_PAGE_NUMBER;
        Integer queryPageSize = BeerServiceJPA.DEFAULT_PAGE_SIZE;

        if(pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;       // API is 1-indexed, while PageRequest is 0-indexed
        }

        if(pageSize != null && pageSize > 0) {
            queryPageSize = Math.min(pageSize, 3000);
        }
        return PageRequest.of(queryPageNumber, queryPageSize);
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
