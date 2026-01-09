package routing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import routing.config.TwoGisConfig;
import routing.dto.GeocodeResponse;
import routing.dto.TwoGisRequest;
import routing.dto.TwoGisResponse;

@FeignClient(
        name = "twoGisClientGeocode",
        url= "https://catalog.api.2gis.com/3.0/items",
        configuration = TwoGisConfig.class
)
public interface TwoGIsClientGeocode {
    @GetMapping("/geocode")
    GeocodeResponse getGeocode(
            @RequestParam("q") String address,
            @RequestParam("fields") String fields,
            @RequestParam("key") String key
    );
}
