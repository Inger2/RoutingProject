package routing.service;

import org.springframework.web.bind.annotation.RequestBody;
import routing.dto.TwoGisRequest;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public interface RoutingService {
    TwoGisRequest getRequest(String initAddress, String destAddress, long utcTime);
    void notificationLogic(TwoGisRequest request, ZonedDateTime arrivalTime);
public ZonedDateTime mapUserTime(LocalTime localTime, ZoneId zoneId);
    public boolean isWorkingDay(ZonedDateTime zonedDateTime);
     ZonedDateTime calculateDepartureTime(TwoGisRequest request, ZonedDateTime arrivalTime);
}
