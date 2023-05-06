package nabil.springmvcrest.beer.services;

import nabil.springmvcrest.beer.entities.Beer;
import nabil.springmvcrest.beer.model.BeerCSV;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BeerCSVServiceTest {

    BeerCSVService beerCSVService = new BeerCSVService();
    @Test
    void getBeerCSVList() throws FileNotFoundException {
        File csvFile = ResourceUtils.getFile("classpath:csvdata/beers.csv");
        List<BeerCSV> beerCSVList = beerCSVService.getBeerCSVList();
        assertEquals(2410, beerCSVList.size());
    }
}