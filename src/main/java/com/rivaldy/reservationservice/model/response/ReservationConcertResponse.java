package com.rivaldy.reservationservice.model.response;

import com.rivaldy.reservationservice.util.StatusEnum;

public record ReservationConcertResponse(String name, String description, String location,
                                         String venue, String date, StatusEnum status) {
}
