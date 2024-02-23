package com.rivaldy.reservationservice.service;

import com.rivaldy.reservationservice.model.entity.Concert;
import com.rivaldy.reservationservice.model.entity.SeatType;
import com.rivaldy.reservationservice.model.entity.SeatTypeDetail;
import com.rivaldy.reservationservice.model.request.AddSeatTypeRequest;
import com.rivaldy.reservationservice.model.request.SeatTypeRequest;
import com.rivaldy.reservationservice.model.response.*;
import com.rivaldy.reservationservice.repository.ConcertRepository;
import com.rivaldy.reservationservice.repository.SeatTypeDetailRepository;
import com.rivaldy.reservationservice.repository.SeatTypeRepository;
import com.rivaldy.reservationservice.util.StatusEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.rivaldy.reservationservice.util.MessageConstant.ADD_SEAT_TYPE;

@Service
@AllArgsConstructor
public class SeatService {

    private final ConcertRepository concertRepository;
    private final SeatTypeRepository seatTypeRepository;
    private final SeatTypeDetailRepository seatTypeDetailRepository;

    public GeneralResponse addSeat(AddSeatTypeRequest request){
        Concert concert = concertRepository.findById(request.getConcertId())
                .orElseThrow(() -> new RuntimeException("Not Found"));

        addSingleSeat(concert, request);
        AddBaseSeatTypeResponse baseSeat = new AddBaseSeatTypeResponse(request.getName(), request.getType(), request.getTotalSeat());
        AddSeatTypeResponse response = new AddSeatTypeResponse(concert.getName(), baseSeat);
        return new GeneralResponse(ADD_SEAT_TYPE, response);
    }

    private void addSingleSeat(Concert concert, SeatTypeRequest request){
        SeatType seatType = new SeatType();
        seatType.setConcert(concert);
        seatType.setName(request.getName());
        seatType.setType(request.getType());
        seatTypeRepository.save(seatType);

        List<SeatTypeDetail> seatDetails = new ArrayList<>();
        for (int i = 1; i <= request.getTotalSeat(); i++) {
            SeatTypeDetail detail = new SeatTypeDetail();
            detail.setSeatType(seatType);
            detail.setPrice(request.getPrice());
            detail.setSeatNumber(request.getType() + i);
            detail.setStatus(StatusEnum.AVAILABLE);
            seatDetails.add(detail);
        }
        seatTypeDetailRepository.saveAll(seatDetails);
    }

    public GeneralResponse addMultipleSeat(Integer concertId, List<SeatTypeRequest> requests){
        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(() -> new RuntimeException("Not Found"));
        requests.forEach(r -> addSingleSeat(concert, r));
        List<AddBaseSeatTypeResponse> baseSeats = new ArrayList<>();
        requests.forEach(r -> baseSeats.add(new AddBaseSeatTypeResponse(r.getName(), r.getType(), r.getTotalSeat())));
        AddMultipleSeatTypeResponse response = new AddMultipleSeatTypeResponse(concert.getName(), baseSeats);
        return new GeneralResponse(ADD_SEAT_TYPE, response);
    }

//    public GeneralResponse getConcertSeat(Integer concertId){
//        Concert concert = concertRepository.findById(concertId)
//                .orElseThrow(() -> new RuntimeException("Not Found"));
//
//        List<SeatType> seatTypes = seatTypeRepository.findAllByConcert(concertId);
//        List<SeatResponse> seatResponses = new ArrayList<>();
//        seatTypes.forEach(s -> {
//            List<SeatTypeDetail> details = s.getSeatDetail();
//            BigDecimal price = seatTypeDetailRepository.getMinimumPrice(s.getId());
//            int available = seatTypeDetailRepository.countAvailableSeat(s.getId());
//            String availableSeat = String.format("%s / %s", available, details.size());
//            SeatResponse seat = new SeatResponse(s.getType(), s.getName(), price, availableSeat);
//            seatResponses.add(seat);
//        });
//        ConcertResponse concertResponse = new ConcertResponse(concertId, concert.getName(), concert.getLocation(), concert.getVenue(), convert(concert.getDateTime()));
//        ConcertSeatResponse response = new ConcertSeatResponse(concertResponse, seatResponses);
//        return new GeneralResponse("Get all concert seat", response);
//    }

    public void getAllConcertSeat(Integer concertId, List<SeatResponse> seatResponses){
        List<SeatType> seatTypes = seatTypeRepository.findAllByConcert(concertId);
        seatTypes.forEach(s -> {
            List<SeatTypeDetail> details = s.getSeatDetail();
            BigDecimal price = seatTypeDetailRepository.getMinimumPrice(s.getId());
            int available = seatTypeDetailRepository.countAvailableSeat(s.getId());
            String availableSeat = String.format("%s / %s", available, details.size());
            SeatResponse seat = new SeatResponse(s.getType(), s.getName(), price, availableSeat);
            seatResponses.add(seat);
        });
    }

}
