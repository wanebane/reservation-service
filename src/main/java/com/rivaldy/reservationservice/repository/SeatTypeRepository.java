package com.rivaldy.reservationservice.repository;

import com.rivaldy.reservationservice.model.entity.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatTypeRepository extends JpaRepository<SeatType, Integer> {

    @Query("select s from SeatType s inner join Concert c on s.concert.id = c.id where s.concert.id = :concertId")
    List<SeatType> findAllByConcert(Integer concertId);
}
