package controller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final Map<String, User> usersMap = new HashMap<>();

    @PostMapping
    public User createUser(@RequestBody User user) {

        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {

      return usersMap.get(user.getEmail());
    }

    @GetMapping
    Collection<User> getUsers() {
        log.debug("пользователей в текущий момент: {}", usersMap.size());
        return usersMap.values();
    }
}
