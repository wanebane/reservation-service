package com.rivaldy.reservationservice.model.entity;

import com.rivaldy.reservationservice.util.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "seat_type_detail")
public class SeatTypeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seat_detail_id_seq")
    @SequenceGenerator(name = "seat_detail_id_seq", sequenceName = "seat_detail_id_seq", allocationSize = 1)
    @Column(name = "id", insertable = false, updatable = false)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_type")
    private SeatType seatType;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "seat_number", length = 10)
    private String seatNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10)
    private StatusEnum status;
}
