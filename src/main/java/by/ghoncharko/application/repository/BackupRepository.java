package by.ghoncharko.application.repository;

import by.ghoncharko.application.entity.Backup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BackupRepository extends JpaRepository<Backup, Long> {
}
