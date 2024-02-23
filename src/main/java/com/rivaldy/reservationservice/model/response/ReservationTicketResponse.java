package com.rivaldy.reservationservice.model.response;

import com.rivaldy.reservationservice.util.StatusEnum;

import java.math.BigDecimal;
import java.util.List;

public record ReservationTicketResponse(String id, String createdAt,
                                        BigDecimal totalPrice, StatusEnum status,
                                        Object concert,
                                        Object seats) {
}
