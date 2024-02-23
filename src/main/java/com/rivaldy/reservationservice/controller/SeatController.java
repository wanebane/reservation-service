package com.rivaldy.reservationservice.controller;

import com.rivaldy.reservationservice.model.request.AddSeatTypeRequest;
import com.rivaldy.reservationservice.model.request.SeatTypeRequest;
import com.rivaldy.reservationservice.model.response.GeneralResponse;
import com.rivaldy.reservationservice.service.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/seat")
public class SeatController {

    private final SeatService seatService;
    @PostMapping("/add")
    public ResponseEntity<GeneralResponse> addSeat(@RequestBody AddSeatTypeRequest request){
        return new ResponseEntity<>(seatService.addSeat(request), HttpStatus.CREATED);
    }

    @PostMapping("/add-multiple/{concertId}")
    public ResponseEntity<GeneralResponse> addMultipleSeat(@PathVariable Integer concertId, @RequestBody List<SeatTypeRequest> request){
        return new ResponseEntity<>(seatService.addMultipleSeat(concertId, request), HttpStatus.CREATED);
    }

}
