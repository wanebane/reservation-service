package com.rivaldy.reservationservice.model.request;

import lombok.Data;

import java.util.List;

@Data
public class ReservationRequest {

    private Integer concertId;
    private String seatType;
    private Integer quantity;

}
