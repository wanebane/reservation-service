package com.rivaldy.reservationservice.repository;

import com.rivaldy.reservationservice.model.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConcertRepository extends JpaRepository<Concert, Integer> {

    Optional<Concert> findByName(String name);
}
