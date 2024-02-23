package com.rivaldy.reservationservice.model.entity;

import com.rivaldy.reservationservice.util.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "ticket_detail")
public class TicketDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ticket_detail_id_seq")
    @SequenceGenerator(name = "ticket_detail_id_seq", sequenceName = "ticket_detail_id_seq", allocationSize = 1)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket")
    private Ticket ticket;
    @Column(name = "seat_type", length = 2, nullable = false)
    private String seatType;
    @Column(name = "seat_name", length = 20, nullable = false)
    private String seatName;
    @Column(name = "seat_number", length = 10, nullable = false)
    private String seatNumber;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10, nullable = false)
    private StatusEnum status;
}
