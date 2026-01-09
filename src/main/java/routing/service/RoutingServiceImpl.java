package routing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import routing.client.TwoGIsClientGeocode;
import routing.client.TwoGisClientRouting;
import routing.config.TwoGisConfig;
import routing.dto.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RoutingServiceImpl implements RoutingService {
    private final TwoGisClientRouting twoGisClientRouting;
    private final TwoGIsClientGeocode twoGisClientGeocode;
    private final TwoGisConfig twoGisConfig;
    private static final Duration BUFFER_TIME = Duration.ofMinutes(30);
    public void notificationLogic(TwoGisRequest request, ZonedDateTime arrivalTime) {
            ZonedDateTime targetTime = calculateDepartureTime(request, arrivalTime);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedTime = targetTime.format(formatter);
            System.out.println("Calculated departure time is " + formattedTime);
    }

    public boolean isWorkingDay(ZonedDateTime zonedDateTime) {
        DayOfWeek currentDay = zonedDateTime.getDayOfWeek();
        return currentDay != DayOfWeek.SATURDAY && currentDay != DayOfWeek.SUNDAY;
    }

    public ZonedDateTime calculateDepartureTime(TwoGisRequest request, ZonedDateTime arrivalTime) {
        Duration duration = Duration.ofSeconds(getTime(request).total_duration());
        return arrivalTime.minusSeconds(duration.toSeconds());
    }

    private TwoGisResponse.Result getTime(TwoGisRequest request) {
        String key = twoGisConfig.getKey();
        TwoGisResponse response = twoGisClientRouting.fetchTime(request, key);
        System.out.println(response);
        TwoGisResponse.Result result = response
                .result()
                .stream()
                .min(Comparator.comparing(TwoGisResponse.Result::total_duration))
                .orElse(null);
//        Result result = new Result(870);
        System.out.println(result); // TwoGisResponse[result=[Result[total_duration=855]]
        return result;
    }

    public TwoGisRequest getRequest(String initAddress, String destAddress, long utcTime) {
        GeocodeResponse.Point originalAddress = getCoordinates(initAddress); //Point[lon=37.532083, lat=55.740486]
        GeocodeResponse.Point destinationAddress = getCoordinates(destAddress); //Point[lon=37.57899, lat=55.774293]
        Point initPoint = new Point(
                "stop",
                originalAddress.lon(),
                originalAddress.lat()
        );
        Point destPoint = new Point(
                "stop",
                destinationAddress.lon(),
                destinationAddress.lat()
        );
        System.out.println(utcTime);
        return new TwoGisRequest(List.of(initPoint, destPoint), utcTime, "statistics");
    }

    private GeocodeResponse.Point getCoordinates(String address) {
        String key = twoGisConfig.getKey();
        GeocodeResponse response = twoGisClientGeocode.getGeocode(address, "items.point", key);
        return response.result().items().stream()
                .findFirst()
                .map(GeocodeResponse.Item::point)
                .orElse(null);
    }

    public ZonedDateTime mapUserTime(LocalTime localTime, ZoneId zoneId) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), localTime);
        return localDateTime.atZone(zoneId);
    }
}
