package com.rivaldy.reservationservice.service;

import com.rivaldy.reservationservice.model.entity.*;
import com.rivaldy.reservationservice.model.response.*;
import com.rivaldy.reservationservice.repository.TicketDetailRepository;
import com.rivaldy.reservationservice.repository.TicketRepository;
import com.rivaldy.reservationservice.util.StatusEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.rivaldy.reservationservice.util.FormatMapper.convert;
import static com.rivaldy.reservationservice.util.MessageConstant.ALL_TICKET_FIND;
import static com.rivaldy.reservationservice.util.MessageConstant.DETAIL_TICKET_FIND;

@Service
@AllArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketDetailRepository ticketDetailRepository;
    private final JwtService jwtService;

    public void createReservationTicket(User user, Concert concert,
                                        List<SeatTypeDetail> seatDetails,
                                        List<TicketDetail> ticketDetails){

        BigDecimal totalPrice = seatDetails.stream()
                .map(SeatTypeDetail::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        Ticket ticket = new Ticket();
        ticket.setTotalPrice(totalPrice);
        ticket.setStatus(StatusEnum.ACTIVE);
        ticket.setCreatedBy(user);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setConcert(concert);
        ticketRepository.save(ticket);

        seatDetails.forEach(s -> {
            TicketDetail detail = new TicketDetail();
            detail.setTicket(ticket);
            detail.setSeatType(s.getSeatType().getType());
            detail.setSeatName(s.getSeatType().getName());
            detail.setSeatNumber(s.getSeatNumber());
            detail.setPrice(s.getPrice());
            detail.setStatus(StatusEnum.RESERVED);
            ticketDetails.add(detail);
        });
        ticketDetailRepository.saveAll(ticketDetails);
    }

    public GeneralResponse getAllTicket(){
        List<Ticket> tickets = ticketRepository.findByUser(jwtService.getUsername());
        List<TicketResponse> ticketResponses = new ArrayList<>();
        tickets.forEach(t -> ticketResponses.add(new TicketResponse(t.getId().toString(),
                t.getConcert().getName(), t.getConcert().getLocation(), t.getConcert().getVenue(),
                convert(t.getCreatedAt()), t.getTotalPrice(), t.getStatus())));

        return new GeneralResponse(ALL_TICKET_FIND, ticketResponses);
    }

    public GeneralResponse getDetail(Integer ticketId){
        Ticket ticket = ticketRepository
                .findByUserAndTicket(jwtService.getUsername(), ticketId)
                .orElseThrow(() -> new RuntimeException("Not found!"));
        Concert concert = ticket.getConcert();

        ConcertResponse concertResponse = new ConcertResponse(concert.getId(), concert.getName(),
                concert.getLocation(), concert.getVenue(), convert(concert.getDateTime()));
        List<TicketDetailResponse> detailResponses = new ArrayList<>();
        ticket.getDetails().forEach(d -> detailResponses.add(new TicketDetailResponse(d.getSeatType(),
                d.getSeatName(), d.getSeatNumber(), d.getPrice(), d.getStatus())));

        ReservationTicketResponse ticketResponse = new ReservationTicketResponse(ticketId.toString(),
                convert(ticket.getCreatedAt()), ticket.getTotalPrice(), ticket.getStatus(), concertResponse, detailResponses);
        return new GeneralResponse(DETAIL_TICKET_FIND, ticketResponse);
    }
}
