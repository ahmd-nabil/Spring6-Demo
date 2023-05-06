package nabil.springmvcrest.beer.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//"row","count.x","abv","ibu","id","beer","style","brewery_id","ounces","style2","count.y","brewery","city","state","label"
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeerCSV {
    @CsvBindByName(column = "row")
    private Integer row;

    @CsvBindByName(column = "count.x")
    private Integer count_x;

    @CsvBindByName(column = "abv")
    private String abv;

    @CsvBindByName(column = "ibu")
    private String ibu;

    @CsvBindByName(column = "id")
    private Integer id;

    @CsvBindByName(column = "beer")
    private String name;

    @CsvBindByName(column = "style")
    private String style;

    @CsvBindByName(column = "brewery_id")
    private String breweryId;

    @CsvBindByName(column = "ounces")
    private Float ounces;

    @CsvBindByName(column = "style2")
    private String style2;

    @CsvBindByName(column = "count.y")
    private Integer count_y;

    @CsvBindByName(column = "brewery")
    private String brewery;

    @CsvBindByName(column = "city")
    private String city;

    @CsvBindByName(column = "state")
    private String state;

    @CsvBindByName(column = "label")
    private String label;
}
