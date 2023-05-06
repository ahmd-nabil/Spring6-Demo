package nabil.springmvcrest.beer.mappers;

import nabil.springmvcrest.beer.entities.Beer;
import nabil.springmvcrest.beer.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO beerDTO);
    BeerDTO beerToBeerDto(Beer beer);
}
