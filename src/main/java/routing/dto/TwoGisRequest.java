package routing.dto;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

public record TwoGisRequest(List<Point> points, Long utc, String traffic_mode) {

}
