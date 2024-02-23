package com.rivaldy.reservationservice.controller;

import com.rivaldy.reservationservice.model.request.AuthenticationRequest;
import com.rivaldy.reservationservice.model.request.RegistrationRequest;
import com.rivaldy.reservationservice.model.response.GeneralResponse;
import com.rivaldy.reservationservice.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<GeneralResponse> register(
            @RequestBody RegistrationRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse> login(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
