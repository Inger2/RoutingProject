package routing.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public record TgRegistrationDTO(
        long chatId,
        String userAddress,
        String destAddress,
        ZoneId userTimeZone,
        LocalTime arrivalTime
) {
}
