package routing.service;

import org.springframework.web.bind.annotation.RequestBody;
import routing.model.User;

public interface UserService {
    User createUser(User user);
}
