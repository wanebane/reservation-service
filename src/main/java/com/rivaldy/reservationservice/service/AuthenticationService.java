package com.rivaldy.reservationservice.service;

import com.rivaldy.reservationservice.model.entity.Token;
import com.rivaldy.reservationservice.model.entity.User;
import com.rivaldy.reservationservice.model.request.AuthenticationRequest;
import com.rivaldy.reservationservice.model.request.RegistrationRequest;
import com.rivaldy.reservationservice.model.response.AuthenticationResponse;
import com.rivaldy.reservationservice.model.response.GeneralResponse;
import com.rivaldy.reservationservice.model.response.RegistrationResponse;
import com.rivaldy.reservationservice.repository.TokenRepository;
import com.rivaldy.reservationservice.repository.UserRepository;
import com.rivaldy.reservationservice.util.StatusEnum;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rivaldy.reservationservice.util.MessageConstant.*;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public GeneralResponse register(RegistrationRequest request){

        if (userRepository.findByUsername(request.getUsername()).isPresent()){
            //throw error
            return new GeneralResponse(USER_ALREADY_EXIST, null);
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(request.getRole());
        user.setStatus(StatusEnum.ACTIVE);

        user = userRepository.save(user);
        String token = jwtService.generateToken(user);
        saveUserToken(token, user);

        return new GeneralResponse(USER_REGISTRATION, new RegistrationResponse(request.getFirstName(), request.getLastName(), token));
    }

    public GeneralResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(token, user);

        return new GeneralResponse(USER_LOGIN_SUCCESS, new AuthenticationResponse(user.getUsername(), token));
    }

    private void revokeAllTokenByUser(User user){
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if (validTokens.isEmpty()){
            return;
        }

        validTokens.forEach(t -> t.setLoggedOut(true));
        tokenRepository.saveAll(validTokens);
    }

    private void saveUserToken(String jwt, User user){
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}
