package com.rivaldy.reservationservice.model.response;

import java.util.List;

public record AddMultipleSeatTypeResponse(String concert, List<AddBaseSeatTypeResponse> seats) {
}
