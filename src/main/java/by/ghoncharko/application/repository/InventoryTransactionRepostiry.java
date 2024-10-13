package by.ghoncharko.application.repository;

import by.ghoncharko.application.entity.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryTransactionRepostiry extends JpaRepository<InventoryTransaction, Integer> {
}
