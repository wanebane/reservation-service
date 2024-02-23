package com.rivaldy.reservationservice.service;

import com.rivaldy.reservationservice.model.entity.Concert;
import com.rivaldy.reservationservice.model.request.AddConcertRequest;
import com.rivaldy.reservationservice.model.response.*;
import com.rivaldy.reservationservice.repository.ConcertRepository;
import com.rivaldy.reservationservice.util.StatusEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.rivaldy.reservationservice.util.FormatMapper.convert;
import static com.rivaldy.reservationservice.util.MessageConstant.*;

@Service
@AllArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final SeatService seatService;
    private final JwtService jwtService;

    public GeneralResponse getAllConcert(){
        List<Concert> concerts = concertRepository.findAll();
        List<ConcertResponse> concertResponses = new ArrayList<>();
        concerts.forEach(c -> concertResponses.add(new ConcertResponse(c.getId(), c.getName(), c.getLocation(),
                c.getVenue(), convert(c.getDateTime()))));
        return new GeneralResponse(GET_ALL_CONCERT, concertResponses);
    }

    public GeneralResponse showDetail(Integer id){
        Concert concert = concertRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found!"));
        List<SeatResponse> seatResponses = new ArrayList<>();
        seatService.getAllConcertSeat(id, seatResponses);
        ConcertResponse concertResponse = new ConcertResponse(id, concert.getName(), concert.getLocation(), concert.getVenue(), convert(concert.getDateTime()));
        ConcertSeatResponse response = new ConcertSeatResponse(concertResponse, seatResponses);
        return new GeneralResponse(GET_CONCERT, response);
    }

    public GeneralResponse addConcert(AddConcertRequest request){
        if (concertRepository.findByName(request.getName()).isPresent()){

        }

        Concert concert = new Concert();
        concert.setName(request.getName());
        concert.setLocation(request.getLocation());
        concert.setVenue(request.getVenue());
        concert.setDescription(request.getDescription());
        concert.setDateTime(convert(request.getDate(), request.getTime()));
        concert.setStatus(StatusEnum.AVAILABLE);
        concert.setCreatedBy(jwtService.getUsername());
        concert.setCreatedAt(LocalDateTime.now());
        concertRepository.save(concert);

        AddConcertResponse response = new AddConcertResponse(concert.getId().toString(), concert.getName(), concert.getLocation(), concert.getVenue(),
                concert.getDescription(), concert.getDateTime(), concert.getStatus().name());
        return new GeneralResponse(ADD_CONCERT_SUCCESS, response);
    }
}
