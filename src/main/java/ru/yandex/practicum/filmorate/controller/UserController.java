package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
@Slf4j

public class UserController {
    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        userService.createUser(user);
        log.debug("Добавлен пользователь: {}", user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        userService.updateUser(user);
        log.debug("Обновлены данные пользователя: {}", user);
        return user;
    }

    @GetMapping
    public List<User> geAllUser() {
        log.debug("Поучен список пользователей.");
        return userService.getListUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        log.debug("Информация о пользователе по id: {}", id);
        return userService.findUserById(id);
    }
}