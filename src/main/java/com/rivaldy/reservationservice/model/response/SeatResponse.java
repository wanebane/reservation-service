package com.rivaldy.reservationservice.model.response;

import java.math.BigDecimal;

public record SeatResponse(String type, String name, BigDecimal price, String available) {
}
