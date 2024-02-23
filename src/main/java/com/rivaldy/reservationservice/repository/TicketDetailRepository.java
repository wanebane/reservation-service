package com.rivaldy.reservationservice.repository;

import com.rivaldy.reservationservice.model.entity.TicketDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketDetailRepository extends JpaRepository<TicketDetail, Integer> {
}
