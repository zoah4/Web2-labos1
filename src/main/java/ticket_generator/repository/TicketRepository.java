package ticket_generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ticket_generator.entity.Ticket;

import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findAllById(UUID id);
    Long countByVatin(String vatin);
}