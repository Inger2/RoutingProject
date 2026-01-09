package routing.dto;

import java.util.List;

public record GeocodeResponse(
        GeocodeResult result
) {
    public record Item(Point point) {

    }

    public record GeocodeResult(List<Item> items, int total) {

    }

    public record Point(double lon, double lat) {

    }
}


