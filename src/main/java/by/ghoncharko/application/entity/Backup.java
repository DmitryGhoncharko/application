package by.ghoncharko.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "backup")
public class Backup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('backup_backup_id_seq'")
    @Column(name = "backup_id", nullable = false)
    private Long id;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "backup_date", nullable = false)
    private Instant backupDate;

    @Column(name = "backup_path", nullable = false, length = 500)
    private String backupPath;

    @Column(name = "backup_type", nullable = false, length = 100)
    private String backupType;

}