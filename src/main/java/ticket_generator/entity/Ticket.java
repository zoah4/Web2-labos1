package ticket_generator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @Column(nullable = false)
    private UUID id;
    @Column(length = 11, nullable = false)
    private String vatin; //OIB
    private String firstName;
    private String lastName;
    private Timestamp createdAt;

    public Ticket() {}

    public Ticket(String vatin, String firstName, String lastName) {
        this.vatin = vatin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

}
