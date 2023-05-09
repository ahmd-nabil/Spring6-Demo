package nabil.springmvcrest.beer.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

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

    @ManyToOne
    @MapsId("beerOrderId")
    private BeerOrder beerOrder;

    @ManyToOne
    @MapsId("beerId")
    private Beer beer;
}
