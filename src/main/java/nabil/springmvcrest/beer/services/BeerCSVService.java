package nabil.springmvcrest.beer.services;

import com.opencsv.bean.CsvToBeanBuilder;
import nabil.springmvcrest.beer.model.BeerCSV;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class BeerCSVService {
    public List<BeerCSV> getBeerCSVList() throws FileNotFoundException {
        File csvFile = ResourceUtils.getFile("classpath:csvdata/beers.csv");
        List<BeerCSV> beerCSVList = new CsvToBeanBuilder<BeerCSV>(new FileReader(csvFile)).withType(BeerCSV.class).build().parse();
        return beerCSVList;
    }
}
