package com.rivaldy.reservationservice.model.response;

import com.rivaldy.reservationservice.util.StatusEnum;

import java.math.BigDecimal;

public record TicketDetailResponse(String type, String name, String number,
                                   BigDecimal price, StatusEnum status) {
}
