package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
@Slf4j
public class FriendsController {

    private final UserService userService;

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
