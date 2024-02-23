package com.rivaldy.reservationservice.controller;

import com.rivaldy.reservationservice.model.response.GeneralResponse;
import com.rivaldy.reservationservice.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/ticket")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<GeneralResponse> getAllTicket(){
        return ResponseEntity.ok(ticketService.getAllTicket());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> getDetail(@PathVariable Integer id){
        return ResponseEntity.ok(ticketService.getDetail(id));
    }
}
