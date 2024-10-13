package by.ghoncharko.application.repository;

import by.ghoncharko.application.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
