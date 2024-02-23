package com.rivaldy.reservationservice.controller;

import com.rivaldy.reservationservice.model.request.AddConcertRequest;
import com.rivaldy.reservationservice.model.response.GeneralResponse;
import com.rivaldy.reservationservice.service.ConcertService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/concert")
public class ConcertController {

    private final ConcertService concertService;
    @GetMapping
    public ResponseEntity<GeneralResponse> getAllConcert(){
        return ResponseEntity.ok(concertService.getAllConcert());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> showDetail(@PathVariable Integer id){
        return ResponseEntity.ok(concertService.showDetail(id));
    }

    @PostMapping("/add")
    public ResponseEntity<GeneralResponse> addConcert(@RequestBody AddConcertRequest request){
        return new ResponseEntity<>(concertService.addConcert(request), HttpStatus.CREATED);
    }
}
