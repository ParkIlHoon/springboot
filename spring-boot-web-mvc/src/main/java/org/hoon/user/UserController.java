package org.hoon.user;

import org.hoon.vo.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/users/create")
    public User createUser(@RequestBody User user) {
        return user;
    }
}
