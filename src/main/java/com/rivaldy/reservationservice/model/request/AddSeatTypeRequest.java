package com.rivaldy.reservationservice.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddSeatTypeRequest extends SeatTypeRequest{

    private Integer concertId;
}
