package com.rivaldy.reservationservice.repository;

import com.rivaldy.reservationservice.model.entity.SeatTypeDetail;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface SeatTypeDetailRepository extends JpaRepository<SeatTypeDetail, Integer> {

    @Query("select MIN(d.price) from SeatTypeDetail d inner join SeatType s " +
            "on d.seatType.id = s.id where d.seatType.id = :seatTypeId")
    BigDecimal getMinimumPrice(Integer seatTypeId);

    @Query("select COUNT(d) from SeatTypeDetail d inner join SeatType s " +
            "on d.seatType.id = s.id where d.seatType.id = :seatTypeId and d.status = 'AVAILABLE'")
    Integer countAvailableSeat(Integer seatTypeId);

    @Query("select COUNT(d) from SeatTypeDetail d " +
            "inner join SeatType s on d.seatType.id = s.id " +
            "inner join Concert c on s.concert.id = c.id " +
            "where c.id = :concertId and " +
            "d.seatType.type = :seatType and d.status = 'AVAILABLE'")
    Integer countAvailableSeatAndConcert(Integer concertId, String seatType);

    @Query("select d from SeatTypeDetail d " +
            "inner join SeatType s on d.seatType.id = s.id " +
            "where s.type = :seatType and d.status = 'AVAILABLE' " +
            "order by d.id, d.seatNumber " +
//            "fetch first :total rows only"
            "limit :totalData"
    )
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    List<SeatTypeDetail> bookAvailableSeat(String seatType, int totalData);

}
