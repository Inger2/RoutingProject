package routing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import routing.dto.NotificationDTO;

@FeignClient(
        name ="notificationClient",
        url = "localhost:8081"
)
public interface NotificationClient {
    @PostMapping("/getMessage")
    void sendNotification(@RequestBody NotificationDTO notificationDTO);
}
