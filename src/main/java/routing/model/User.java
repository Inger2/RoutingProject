package routing.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tg_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private long chatId;

    private String userAddress;

    private String destAddress;

    private ZoneId userTimeZone;

    private LocalTime arrivalTime;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    private ZonedDateTime notificationTime;

    private LocalDateTime lastNotification;
}
