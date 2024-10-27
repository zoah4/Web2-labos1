package ticket_generator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ticket_generator.entity.Ticket;
import ticket_generator.service.TicketService;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    private final TicketService service;
    public TicketController(TicketService service) {
        this.service = service;
    }

    @PostMapping(value ="/generate-qr", produces = "image/png")
    public ResponseEntity<byte[]> generateQRCode(@RequestBody Ticket ticket) {
        return this.service.generateQRCode(ticket);
    }
}
