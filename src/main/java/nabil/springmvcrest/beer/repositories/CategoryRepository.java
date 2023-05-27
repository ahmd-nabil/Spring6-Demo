package nabil.springmvcrest.beer.repositories;

import nabil.springmvcrest.beer.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ahmed Nabil
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
