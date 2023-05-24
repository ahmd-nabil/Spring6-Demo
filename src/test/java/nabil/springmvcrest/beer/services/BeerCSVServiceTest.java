package nabil.springmvcrest.beer.services;

import nabil.springmvcrest.beer.model.BeerCSV;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BeerCSVServiceTest {

    BeerCSVService beerCSVService = new BeerCSVService();
    @Test
    void getBeerCSVList() throws FileNotFoundException {
        List<BeerCSV> beerCSVList = beerCSVService.getBeerCSVList();
        assertEquals(2410, beerCSVList.size());
    }
}