package ticket_generator.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ticket_generator.entity.Ticket;
import ticket_generator.repository.TicketRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TicketService {
    private final TicketRepository repository;
    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    public Long getTicketNumber() {
        return this.repository.count();
    }

    public ResponseEntity<byte[]> generateQRCode(Ticket ticket) {
        if(!inputValidation(ticket)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nisu dobro uneseni svi podaci");
        }
        if(!validation(ticket.getVatin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nije moguće izgenerirati više od 3 ulaznice za ovaj OIB");
        }
        UUID id = generateTicketId();
        ticket.setId(id);
        ticket.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        save(ticket);
        String url = "https://ticket-app-web2-lab1.onrender.com/ticket-info/" + id;
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 300, 300);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);
            return ResponseEntity.ok(outputStream.toByteArray());
        } catch(WriterException|IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    public Ticket getTicketInfo(UUID id) {
        return this.repository.findAllById(id);
    }

    public boolean validation(String vatin) {
        return this.repository.countByVatin(vatin) != 3;
    }

    public boolean inputValidation(Ticket ticket) {
        return ticket.getVatin() != null &&
                ticket.getFirstName() != null &&
                ticket.getLastName() != null &&
                ticket.getVatin().length() == 11;
    }

    public UUID generateTicketId() {
        return UUID.randomUUID();
    }

    public void save(Ticket ticket) {
        this.repository.save(ticket);
    }

}
