package routing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import routing.dto.TgRegistrationDTO;
import routing.dto.TwoGisRequest;
import routing.mapper.UserMapper;
import routing.model.User;
import routing.service.RoutingService;
import routing.service.UserService;

import java.time.ZonedDateTime;


@RestController
@RequiredArgsConstructor
public class TgController {
    private final UserService userService;
    private final RoutingService routingService;
    private final UserMapper userMapper;

    @PostMapping("/createUser")
    public User createUser(@RequestBody TgRegistrationDTO tgRegistrationDTO) {
        User user=userMapper.toUserFromRegistration(tgRegistrationDTO);
        userService.createUser(user);
        return user;
    }
}
