package routing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import routing.config.TwoGisConfig;
import routing.service.RoutingService;

import java.time.*;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(TwoGisConfig.class)
public class SpringBootApp {
    public static void main(String[] args) {
         ApplicationContext applicationContext = SpringApplication.run(SpringBootApp.class, args);
        RoutingService routingService = applicationContext.getBean(RoutingService.class);
//        FeignConfig feignConfig = applicationContext.getBean(FeignConfig.class);
//        LocalTime arrivalTime = LocalTime.of(10,15,0);
//        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), arrivalTime);
//        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Europe/Kyiv"));
////        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
//
//
//        String ogAddress = "Москва, Кутузовский проспект 32";
//        String destAddress = "Грузинский Вал улица, 11 ст3";
//            TwoGisRequest request = routingService.getRequest(ogAddress,destAddress,zonedDateTime.toEpochSecond());
//        routingService.notificationLogic(request, zonedDateTime);
//        System.out.println(routingService.getCoordinates("Москва, Кутузовский проспект 32"));
    }

}
