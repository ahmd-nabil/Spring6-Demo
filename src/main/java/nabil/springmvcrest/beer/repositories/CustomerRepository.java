package nabil.springmvcrest.beer.repositories;

import nabil.springmvcrest.beer.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
