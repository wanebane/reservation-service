package com.rivaldy.reservationservice.model.response;

import com.rivaldy.reservationservice.util.StatusEnum;

import java.math.BigDecimal;

public record TicketResponse(String id, String concert, String location, String venue,
                             String createdAt, BigDecimal totalPrice, StatusEnum status) {
}
