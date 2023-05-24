package nabil.springmvcrest.beer.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
//@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class BeerOrderLine {
    public BeerOrderLine(BeerOrderLineId beerOrderLineId, Integer orderQuantity, Integer quantityAllocated, Integer version, BeerOrder beerOrder, Beer beer) {
        this.beerOrderLineId = beerOrderLineId;
        this.orderQuantity = orderQuantity;
        this.quantityAllocated = quantityAllocated;
        this.version = version;
        setBeerOrder(beerOrder);
        setBeer(beer);
    }

    @EmbeddedId BeerOrderLineId beerOrderLineId;

    private Integer orderQuantity = 0;

    private Integer quantityAllocated = 0;

    @Version
    private Integer version;

    @ManyToOne(optional = false)
    @MapsId("beerOrderId")
    private BeerOrder beerOrder;

    public void setBeerOrder(BeerOrder beerOrder) {
        this.beerOrder = beerOrder;
        this.beerOrder.getBeerOrderLines().add(this);
    }

    @ManyToOne(optional = false)
    @MapsId("beerId")
    private Beer beer;

    public void setBeer(Beer beer) {
        this.beer = beer;
        this.beer.getBeerOrderLines().add(this);
    }
}
