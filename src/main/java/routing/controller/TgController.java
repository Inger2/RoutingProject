package routing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import routing.dto.TgRegistrationDTO;
import routing.mapper.UserMapper;
import routing.model.User;
import routing.service.UserService;


@RestController
@RequiredArgsConstructor
public class TgController {
    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/createUser")
    public User createUser(@RequestBody TgRegistrationDTO tgRegistrationDTO) {
        User user=userMapper.toUserFromRegistration(tgRegistrationDTO);
        userService.createUser(user);
        return user;
    }
}
