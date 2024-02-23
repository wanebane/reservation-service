package com.rivaldy.reservationservice.controller;

import com.rivaldy.reservationservice.model.request.ReservationRequest;
import com.rivaldy.reservationservice.model.response.GeneralResponse;
import com.rivaldy.reservationservice.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<GeneralResponse> createTicket(@RequestBody ReservationRequest request){
        return new ResponseEntity<>(reservationService.createTicket(request), HttpStatus.CREATED);
    }
}
