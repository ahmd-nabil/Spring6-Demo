package nabil.springmvcrest.beer.repositories;

import nabil.springmvcrest.beer.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
