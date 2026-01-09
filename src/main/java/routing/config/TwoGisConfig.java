package routing.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter
@Setter
@ConfigurationProperties(prefix = "two-gis")
public class TwoGisConfig {
    private String key;
}
