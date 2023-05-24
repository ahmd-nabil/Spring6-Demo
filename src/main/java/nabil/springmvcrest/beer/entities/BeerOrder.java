package nabil.springmvcrest.beer.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
//@AllArgsConstructor  Builder pattern uses @AllArgsConstructor, and I wanna set customer manually to deal with bidirectional relationship
@NoArgsConstructor
@Builder
@Setter
@Getter
public class BeerOrder {

    public BeerOrder(UUID id, LocalDateTime createdDate, LocalDateTime updatedDate, Integer version, Customer customer, Set<BeerOrderLine> beerOrderLines) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.version = version;
        setCustomer(customer);
        this.beerOrderLines = beerOrderLines;
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedDate;

    @Version
    private Integer version;

    @ManyToOne(optional = false)
    private Customer customer;
    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.getBeerOrders().add(this);
    }

    @OneToMany(mappedBy = "beerOrder")
    private Set<BeerOrderLine> beerOrderLines;
}
