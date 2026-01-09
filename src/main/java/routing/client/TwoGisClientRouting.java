package routing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import routing.config.TwoGisConfig;
import routing.dto.TwoGisRequest;
import routing.dto.TwoGisResponse;

@FeignClient(
        name = "twoGisClientRouting",
        url= "http://routing.api.2gis.com/routing/7.0.0",
        configuration = TwoGisConfig.class
)
public interface TwoGisClientRouting {
    @PostMapping(value = "/global?key={key}")
    TwoGisResponse fetchTime(@RequestBody TwoGisRequest request, @PathVariable String key);
}
