package routing.service;

import routing.dto.TwoGisRequest;

import java.time.ZonedDateTime;

public interface RoutingService {
    TwoGisRequest getRequest(String initAddress, String destAddress, long utcTime);

    boolean isWorkingDay(ZonedDateTime zonedDateTime);

    ZonedDateTime calculateDepartureTime(TwoGisRequest request, ZonedDateTime arrivalTime);
}
