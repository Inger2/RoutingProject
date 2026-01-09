package routing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import routing.client.TwoGIsClientGeocode;
import routing.client.TwoGisClientRouting;
import routing.config.TwoGisConfig;
import routing.dto.GeocodeResponse;
import routing.dto.TwoGisRequest;
import routing.dto.TwoGisResponse;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutingServiceImpl implements RoutingService {
    private final TwoGisClientRouting twoGisClientRouting;

    private final TwoGIsClientGeocode twoGisClientGeocode;

    private final TwoGisConfig twoGisConfig;


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
        return response
                .result()
                .stream()
                .min(Comparator.comparing(TwoGisResponse.Result::total_duration))
                .orElse(null);
    }

    public TwoGisRequest getRequest(String initAddress, String destAddress, long utcTime) {
        GeocodeResponse.Point originalAddress = getCoordinates(initAddress); //Point[lon=37.532083, lat=55.740486]
        GeocodeResponse.Point destinationAddress = getCoordinates(destAddress); //Point[lon=37.57899, lat=55.774293]
        TwoGisRequest.Point initPoint = new TwoGisRequest.Point(
                "stop",
                originalAddress.lon(),
                originalAddress.lat()
        );
        TwoGisRequest.Point destPoint = new TwoGisRequest.Point(
                "stop",
                destinationAddress.lon(),
                destinationAddress.lat()
        );
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

}
