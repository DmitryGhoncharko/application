package by.ghoncharko.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "audit_log")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('audit_log_log_id_seq'")
    @Column(name = "log_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "action", nullable = false)
    private String action;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "\"timestamp\"", nullable = false)
    private Instant timestamp;

    @Column(name = "details", length = Integer.MAX_VALUE)
    private String details;

}