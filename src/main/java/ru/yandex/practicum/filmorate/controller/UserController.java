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

    @PutMapping("/{id}/friends/{friendId}")
    public void createFriend(@PathVariable long id, @PathVariable long friendId) {

        userService.createFriends(id, friendId);
        log.debug("Пользователь : {} добавлен в друзья к : {}", id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteOfFriends(@PathVariable long id, @PathVariable long friendId) {
        userService.deleteFriends(id, friendId);
        log.debug("Пользователь : {} удален из друзей : {}", id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable long id) {
        log.debug("Получен список друзей пользователя: {} друзей", id);
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriendsUser(@PathVariable long id, @PathVariable long otherId) {
        log.debug("Получен список общих друзей пользователя : {} с пользователем : {} ", id, otherId);
        return userService.commonFriends(id, otherId);
    }
}