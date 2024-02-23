package com.rivaldy.reservationservice.model.response;

import com.rivaldy.reservationservice.util.StatusEnum;

public record ReservationSeatResponse(String type, String name, String number, StatusEnum status) {
}
