package com.rivaldy.reservationservice.model.response;

import java.util.List;

public record ConcertSeatResponse(ConcertResponse concert, List<SeatResponse> seats) {
}
