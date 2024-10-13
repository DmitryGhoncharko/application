package by.ghoncharko.application.repository;

import by.ghoncharko.application.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Long countByCategoryId(Long categoryId);
}
