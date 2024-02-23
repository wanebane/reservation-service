package com.rivaldy.reservationservice.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SeatTypeRequest {
    private String name;
    private String type;
    private Integer totalSeat;
    private BigDecimal price;
}
