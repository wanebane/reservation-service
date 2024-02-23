package com.rivaldy.reservationservice.service;

import com.rivaldy.reservationservice.model.entity.*;
import com.rivaldy.reservationservice.model.request.ReservationRequest;
import com.rivaldy.reservationservice.model.response.GeneralResponse;
import com.rivaldy.reservationservice.model.response.ReservationConcertResponse;
import com.rivaldy.reservationservice.model.response.ReservationSeatResponse;
import com.rivaldy.reservationservice.model.response.ReservationTicketResponse;
import com.rivaldy.reservationservice.repository.ConcertRepository;
import com.rivaldy.reservationservice.repository.SeatTypeDetailRepository;
import com.rivaldy.reservationservice.util.StatusEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.rivaldy.reservationservice.util.FormatMapper.convert;
import static com.rivaldy.reservationservice.util.MessageConstant.NOT_FOUND_CONCERT;
import static com.rivaldy.reservationservice.util.MessageConstant.TICKET_SUCCESS;

@Service
@AllArgsConstructor
public class ReservationService {

    private final JwtService jwtService;
    private final ConcertRepository concertRepository;
    private final SeatTypeDetailRepository seatTypeDetailRepository;
    private final TicketService ticketService;

    @Transactional
    public GeneralResponse createTicket(ReservationRequest request){
        Concert concert = concertRepository.findById(request.getConcertId())
                .orElseThrow(() -> new RuntimeException(NOT_FOUND_CONCERT));
        int availableSeat = seatTypeDetailRepository.countAvailableSeatAndConcert(request.getConcertId(), request.getSeatType());
        if (availableSeat < request.getQuantity()){

        }
        List<SeatTypeDetail> seatDetails = seatTypeDetailRepository
                .bookAvailableSeat(request.getSeatType(), request.getQuantity());
        seatDetails.forEach(s -> s.setStatus(StatusEnum.INACTIVE));

        seatTypeDetailRepository.saveAll(seatDetails);

        List<TicketDetail> ticketDetails = new ArrayList<>();
        ticketService.createReservationTicket(jwtService.getUsername(), concert,
                seatDetails, ticketDetails);

        Ticket ticket = ticketDetails.get(0).getTicket();

        List<ReservationSeatResponse> seatResponses = new ArrayList<>();
        ticketDetails.forEach(t -> seatResponses.add(
                new ReservationSeatResponse(t.getSeatType(), t.getSeatName(),
                        t.getSeatNumber(), t.getStatus()))
        );
        ReservationConcertResponse concertResponse = new ReservationConcertResponse(concert.getName(), concert.getDescription(),
                concert.getLocation(), concert.getVenue(), convert(concert.getDateTime()), concert.getStatus());

        ReservationTicketResponse ticketResponse = new ReservationTicketResponse(ticket.getId().toString(),
                convert(ticket.getCreatedAt()), ticket.getTotalPrice(), ticket.getStatus(), concertResponse, seatResponses);
        return new GeneralResponse(TICKET_SUCCESS, ticketResponse);
    }
}
