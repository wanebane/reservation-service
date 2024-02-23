package com.rivaldy.reservationservice.model.response;

public record ConcertResponse(Integer id, String name, String location,
                              String venue, String date) {
}
