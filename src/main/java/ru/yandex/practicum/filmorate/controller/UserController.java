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

    private long userId;
    private final Map<Long, User> usersMap = new HashMap<>();

    private void generatorUserId(User user){
        ++userId;
        user.setId(userId);
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        generatorUserId(user);
        usersMap.put(user.getId(), user);
        log.debug("Добавлен пользователь: {}", user.getName());
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody User user) {

            if (user.getId() == 0 || !usersMap.containsKey(user.getId())) {
                log.debug("Ошибка валидации getId! : {}",user.getId());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(user);
            }
            if (user.getName() == null || user.getName().isBlank()) {
                String name = usersMap.get(user.getId()).getName();
                user.setName(name);
            }
            usersMap.put(user.getId(), user);
            log.debug("Обновлены данные пользователя: {}", user.getName());
            return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<ArrayList<User>> getUsers() {
        log.debug("Сейчас пользователей: {}", usersMap.size());
        return ResponseEntity.ok(new ArrayList<>(usersMap.values()));
    }
}
