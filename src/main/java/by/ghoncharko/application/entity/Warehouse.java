package by.ghoncharko.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "warehouse")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('warehouse_warehouse_id_seq'")
    @Column(name = "warehouse_id", nullable = false)
    private Long id;

    @Column(name = "warehouse_name", nullable = false)
    private String warehouseName;

    @Column(name = "location")
    private String location;

    @Column(name = "capacity")
    private Integer capacity;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

}