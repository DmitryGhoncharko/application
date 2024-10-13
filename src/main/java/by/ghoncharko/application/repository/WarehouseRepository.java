package by.ghoncharko.application.repository;

import by.ghoncharko.application.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
     Long countById(Long id);
}
