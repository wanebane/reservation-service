package com.rivaldy.reservationservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "seat_type")
public class SeatType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seat_type_id_seq")
    @SequenceGenerator(name = "seat_type_id_seq", sequenceName = "seat_type_id_seq", allocationSize = 1)
    @Column(name = "id", insertable = false, updatable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "concert")
    private Concert concert;
    @Column(name = "name", length = 20, nullable = false)
    private String name;
    @Column(name = "type", length = 2, nullable = false)
    private String type;
    @OneToMany(mappedBy = "seatType", cascade = CascadeType.ALL)
    private List<SeatTypeDetail> seatDetail;
}
