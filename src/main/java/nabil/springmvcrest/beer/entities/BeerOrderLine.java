package nabil.springmvcrest.beer.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class BeerOrderLine {
    @EmbeddedId BeerOrderLineId beerOrderLineId;

    private Integer orderQuantity = 0;

    private Integer quantityAllocated = 0;

    @Version
    private Integer version;

    @ManyToOne(optional = false)
    @MapsId("beerOrderId")
    private BeerOrder beerOrder;

    @ManyToOne(optional = false)
    @MapsId("beerId")
    private Beer beer;
}
