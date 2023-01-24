package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@Validated
@RequestMapping("/users")
public class UserController {

    private int createId;

    private final Map<Integer, User> usersMap = new HashMap<>();

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        ++createId;
        user.setId(createId);
        usersMap.put(user.getId(), user);
        log.debug("Добавлен пользователь: {}", user.getName());
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        try {
            if (user.getId() == 0 || !usersMap.containsKey(user.getId())) {
                throw new ValidationException("Ошибка данных.");
            }
            if (user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            usersMap.put(user.getId(), user);
            log.debug("Обновлены данные пользователя: {}", user.getLogin());
            return ResponseEntity.ok(user);
        } catch (ValidationException exception) {
            log.debug(exception.getMessage(),exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(user);
        }
    }

    @GetMapping
    public ResponseEntity<ArrayList<User>> getUsers() {
        log.debug("Сейчас пользователей: {}", usersMap.size());
        return ResponseEntity.ok(new ArrayList<>(usersMap.values()));
    }
}
