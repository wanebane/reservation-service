package com.rivaldy.reservationservice.model.request;

import com.rivaldy.reservationservice.util.RoleEnum;
import lombok.Data;

@Data
public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private RoleEnum role;
}
