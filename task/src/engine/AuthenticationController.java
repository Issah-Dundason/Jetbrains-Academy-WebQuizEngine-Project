package engine;

import engine.security.User;
import engine.security.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class AuthenticationController {
    @Resource
    private UserService userService;

    @PostMapping("/api/register")
    public void registerUser(@RequestBody @Valid User user) {
        userService.registerUser(user);
    }
}
