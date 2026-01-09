package routing.dto;

import java.util.List;

public record TwoGisRequest(List<Point> points, Long utc, String traffic_mode) {

    public record Point(String type, double lon, double lat) {

    }
}
