package com.rivaldy.reservationservice.model.entity;

import com.rivaldy.reservationservice.util.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "concerts")
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "concerts_id_seq")
    @SequenceGenerator(name = "concerts_id_seq", sequenceName = "concerts_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name", length = 30, nullable = false)
    private String name;
    @Column(name = "location", length = 30, nullable = false)
    private String location;
    @Column(name = "venue", length = 30, nullable = false)
    private String venue;
    @Column(name = "description", length = 50, nullable = false)
    private String description;
    @Column(name = "date_time", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime dateTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10, nullable = false)
    private StatusEnum status;
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private User createdBy;
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
}
