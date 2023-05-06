package nabil.springmvcrest.beer.services;

import lombok.extern.slf4j.Slf4j;
import nabil.springmvcrest.beer.model.BeerDTO;
import nabil.springmvcrest.beer.model.BeerStyle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService{

    private Map<UUID, BeerDTO> beerMap;
    public  BeerServiceImpl() {
        beerMap = new HashMap<>();
        BeerDTO beer1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer2 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer3 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    @Override
    public List<BeerDTO> findAll() {
        log.debug("find all beers in our store");
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Optional<BeerDTO> findBeerById(UUID id) {
        log.debug("find Beer By ID in beerServiceImpl.. Id = " + id);
        return Optional.ofNullable(beerMap.get(id));
    }

    @Override
    public BeerDTO addNewBeer(BeerDTO beer) {
        BeerDTO newBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(beer.getVersion())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .quantityOnHand(beer.getQuantityOnHand())
                .price(beer.getPrice())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        beerMap.put(newBeer.getId(), newBeer);
        return newBeer;
    }

    @Override
    public Optional<BeerDTO> updateBeer(UUID id, BeerDTO newBeer) {
        BeerDTO existing = beerMap.get(id);
        existing.setVersion(newBeer.getVersion());
        existing.setBeerName(newBeer.getBeerName());
        existing.setBeerStyle(newBeer.getBeerStyle());
        existing.setUpc(newBeer.getUpc());
        existing.setQuantityOnHand(newBeer.getQuantityOnHand());
        existing.setPrice(newBeer.getPrice());
        existing.setUpdateDate(LocalDateTime.now());
        return Optional.of(existing);
    }

    @Override
    public boolean delete(UUID id) {
        beerMap.remove(id);
        return true;
    }
}
