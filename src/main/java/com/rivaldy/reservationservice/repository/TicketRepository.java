package com.rivaldy.reservationservice.repository;

import com.rivaldy.reservationservice.model.entity.Ticket;
import com.rivaldy.reservationservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("select t from Ticket t where t.createdBy = :userId")
    List<Ticket> findByUser(User userId);

    @Query("select t from Ticket t where t.createdBy = :userId and t.id = :ticketId")
    Optional<Ticket> findByUserAndTicket(User userId, Integer ticketId);
}
