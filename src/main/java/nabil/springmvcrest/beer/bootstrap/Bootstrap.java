package nabil.springmvcrest.beer.bootstrap;

import lombok.RequiredArgsConstructor;
import nabil.springmvcrest.beer.entities.Beer;
import nabil.springmvcrest.beer.entities.Customer;
import nabil.springmvcrest.beer.mappers.BeerMapper;
import nabil.springmvcrest.beer.model.BeerCSV;
import nabil.springmvcrest.beer.model.BeerDTO;
import nabil.springmvcrest.beer.model.BeerStyle;
import nabil.springmvcrest.beer.repositories.BeerRepository;
import nabil.springmvcrest.beer.repositories.CustomerRepository;
import nabil.springmvcrest.beer.services.BeerCSVService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    private final BeerCSVService beerCSVService;
    private final CustomerRepository customerRepository;
    @Override
    public void run(String... args) throws Exception {
        if(beerRepository.count() < 100) {
            populateBeers();
            populateDatabaseWithCSV();
        }
        if(customerRepository.count() == 0) {
            populateCustomers();
        }
    }

    private void populateCustomers() {
        Customer customer1 = Customer.builder()
                .firstName("Ahmed")
                .lastName("Nabil")
                .email("ahm3dnabil99@gmail.com")
                .build();

        Customer customer2 = Customer.builder()
                                .firstName("Sayed")
                                .lastName("Ali")
                                .email("sayedAli@gmail.com")
                                .build();
        customerRepository.save(customer1);
        customerRepository.save(customer2);
    }

    public void populateDatabaseWithCSV() {
        try {
            List<BeerCSV> beerCSVList = beerCSVService.getBeerCSVList();
            beerCSVList.stream().forEach(beerCSV -> {
                BeerStyle beerStyle = switch (beerCSV.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "Cider" -> BeerStyle.GOSE;
                    case "American Pale Ale (APA)" -> BeerStyle.PALE_ALE;
                    case "American Blonde Ale" -> BeerStyle.ALE;
                    case "Hefeweizen" -> BeerStyle.SAISON;
                    case "Berliner Weissbier" -> BeerStyle.PILSNER;
                    case "American Stout" -> BeerStyle.STOUT;
                    case "Oatmeal Stout" -> BeerStyle.WHEAT;
                    case "American Porter" -> BeerStyle.PORTER;
                    default -> BeerStyle.IPA;
                };

                Beer beer = Beer.builder()
                        .beerName(beerCSV.getName().substring(0, Math.min(beerCSV.getName().length(), 50)))
                        .beerStyle(beerStyle)
                        .upc(beerCSV.getIbu())
                        .quantityOnHand(beerCSV.getCount_x())
                        .price(BigDecimal.valueOf(new Random().nextFloat(10, 10000)))
                        .build();
                beerRepository.save(beer);
            });
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateBeers() {
        BeerDTO beer1 = BeerDTO.builder()
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer2 = BeerDTO.builder()
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer3 = BeerDTO.builder()
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerRepository.save(beerMapper.beerDtoToBeer(beer1));
        beerRepository.save(beerMapper.beerDtoToBeer(beer2));
        beerRepository.save(beerMapper.beerDtoToBeer(beer3));
    }
}
