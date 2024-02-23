package com.rivaldy.reservationservice.model.response;

import java.time.LocalDateTime;

public record AddConcertResponse(String id, String name, String location, String venue, String description,
                                 LocalDateTime date, String status) {
}
