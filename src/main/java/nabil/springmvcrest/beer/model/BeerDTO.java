package nabil.springmvcrest.beer.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BeerDTO {
    private UUID id;
    private Integer version;

    @NotBlank
    @NotNull
    private String beerName;

    @NotNull
    private BeerStyle beerStyle;

    @NotNull
    @NotBlank
    private String upc;
    private Integer quantityOnHand;

    @NotNull
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

    BeerDTO(UUID id, Integer version, String beerName, BeerStyle beerStyle, String upc, Integer quantityOnHand, BigDecimal price, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.id = id;
        this.version = version;
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.upc = upc;
        this.quantityOnHand = quantityOnHand;
        this.price = price;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    public static BeerDTOBuilder builder() {
        return new BeerDTOBuilder();
    }

    public String toString() {
        return "Beer.BeerBuilder(id=" + this.id + ", version=" + this.version + ", beerName=" + this.beerName + ", beerStyle=" + this.beerStyle + ", upc=" + this.upc + ", quantityOnHand=" + this.quantityOnHand + ", price=" + this.price + ", createdDate=" + this.createdDate + ", updateDate=" + this.updateDate + ")";
    }

    public static class BeerDTOBuilder {
        private UUID id;
        private Integer version;
        private String beerName;
        private BeerStyle beerStyle;
        private String upc;
        private Integer quantityOnHand;
        private BigDecimal price;
        private LocalDateTime createdDate;
        private LocalDateTime updateDate;

        BeerDTOBuilder() {
        }

        public BeerDTOBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public BeerDTOBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public BeerDTOBuilder beerName(String beerName) {
            this.beerName = beerName;
            return this;
        }

        public BeerDTOBuilder beerStyle(BeerStyle beerStyle) {
            this.beerStyle = beerStyle;
            return this;
        }

        public BeerDTOBuilder upc(String upc) {
            this.upc = upc;
            return this;
        }

        public BeerDTOBuilder quantityOnHand(Integer quantityOnHand) {
            this.quantityOnHand = quantityOnHand;
            return this;
        }

        public BeerDTOBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public BeerDTOBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public BeerDTOBuilder updateDate(LocalDateTime updateDate) {
            this.updateDate = updateDate;
            return this;
        }

        public BeerDTO build() {
            return new BeerDTO(this.id, this.version, this.beerName, this.beerStyle, this.upc, this.quantityOnHand, this.price, this.createdDate, this.updateDate);
        }

        public String toString() {
            return "BeerDTO.BeerDTOBuilder(id=" + this.id + ", version=" + this.version + ", beerName=" + this.beerName + ", beerStyle=" + this.beerStyle + ", upc=" + this.upc + ", quantityOnHand=" + this.quantityOnHand + ", price=" + this.price + ", createdDate=" + this.createdDate + ", updateDate=" + this.updateDate + ")";
        }
    }
}
