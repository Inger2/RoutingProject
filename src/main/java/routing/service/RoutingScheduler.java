package routing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import routing.client.NotificationClient;
import routing.dto.NotificationDTO;
import routing.dto.TwoGisRequest;
import routing.model.User;
import routing.repository.UserRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class RoutingScheduler {
    private final UserRepository userRepository;

    private final RoutingService routingService;

    private final NotificationClient notificationClient;

    private static final Duration CALCULATION_BUFFER = Duration.ofMinutes(120);

    private static final Duration NOTIFICATION_BUFFER = Duration.ofMinutes(30);


    @Scheduled(fixedRate = 300000)
    @Transactional
    public void scheduleRoute() {
        LocalDateTime now = LocalDateTime.now();
        List<User> users = userRepository.findUneditedUsers(now);
        for (User user : users) {
            LocalDate today = LocalDate.now(user.getUserTimeZone());
            ZonedDateTime userCurrentTime = ZonedDateTime.now(user.getUserTimeZone());
            ZonedDateTime arrivalTime = ZonedDateTime.of(today, user.getArrivalTime(), user.getUserTimeZone());
            if (userCurrentTime.isAfter(arrivalTime.minus(CALCULATION_BUFFER)) && routingService.isWorkingDay(userCurrentTime)) {
                TwoGisRequest request = routingService.getRequest(user.getUserAddress(), user.getDestAddress(), arrivalTime.toEpochSecond());
                user.setNotificationTime(routingService.calculateDepartureTime(request, arrivalTime).minus(NOTIFICATION_BUFFER));
                user.setLastModified(LocalDateTime.now());
                userRepository.save(user);
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void ScheduleNotification() {
        LocalDateTime now = LocalDateTime.now();
        List<User> users = userRepository.findUnnotifiedUsers(now);
        for (User user : users) {
            if (user.getNotificationTime() == null) {
                continue;
            }
            ZonedDateTime userCurrentTime = ZonedDateTime.now(user.getUserTimeZone());
            ZonedDateTime sendTime = user.getNotificationTime()
                    .withZoneSameInstant(user.getUserTimeZone());

            if (userCurrentTime.isAfter(sendTime) && routingService.isWorkingDay(userCurrentTime)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                String formattedTime = user.getNotificationTime().plus(NOTIFICATION_BUFFER).withZoneSameInstant(user.getUserTimeZone()).format(formatter);
                notificationClient.sendNotification(new NotificationDTO(user.getChatId(),
                        "Your departure time is " + formattedTime)
                );
                user.setLastNotification(LocalDateTime.now());
                userRepository.save(user);
            }
        }
    }
}




