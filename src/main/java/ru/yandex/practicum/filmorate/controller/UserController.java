package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody User user) {

        userService.createUser(user);
        log.debug("Добавлен пользователь: {}", user.getName());
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {

        userService.updateUser(user);
        log.debug("Обновлены данные пользователя: {}", user.getName());
        return user;
    }

    @GetMapping
    public List<User> geAllUser() {
        log.debug("Сейчас пользователей: {}", userService.getListUsers().size());
        return userService.getListUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        log.debug("Информация о пользователе по id: {}", userService.getThisUserById(id));
        return userService.getThisUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void createFriend(@PathVariable long id, @PathVariable long friendId) {

        userService.createFriends(id, friendId);
        log.debug("Пользователь : {} добавлен в друзья к : {}",
                userService.getThisUserById(id), userService.getThisFriend(friendId));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteOfFriends(@PathVariable long id, @PathVariable long friendId) {
        userService.deleteFriends(id, friendId);
        log.debug("Пользователь : {} удален из друзей : {}",
                userService.getThisUserById(id), userService.getThisFriend(friendId));
    }

    @GetMapping("/{id}/friends")
    public List<User> getHisFriends(@PathVariable long id) {
        log.debug("У пользователя: {} друзей", userService.getFriends(id).size());
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriendsUser(@PathVariable long id, @PathVariable long otherId) {
        log.debug("У пользователь : {} с пользователем : {} общих друзей : {}",
                userService.getThisUserById(id), userService.getThisFriend(otherId),
                userService.commonFriends(id, otherId).size());
        return userService.commonFriends(id, otherId);
    }
}