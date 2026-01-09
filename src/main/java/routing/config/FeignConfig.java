package routing.config;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {
    private final TwoGisConfig twoGis;
    @Bean
    public RequestInterceptor getRequestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("key", twoGis.getKey());
        };
    }
}
