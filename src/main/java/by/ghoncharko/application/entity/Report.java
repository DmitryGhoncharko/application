package by.ghoncharko.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('report_report_id_seq'")
    @Column(name = "report_id", nullable = false)
    private Long id;

    @Column(name = "report_type", nullable = false, length = 100)
    private String reportType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "generated_by", nullable = false)
    private User generatedBy;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "generated_at", nullable = false)
    private Instant generatedAt;

    @Column(name = "file_path", nullable = false, length = 500)
    private String filePath;

}