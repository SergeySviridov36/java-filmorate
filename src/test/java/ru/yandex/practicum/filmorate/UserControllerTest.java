package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FriendDbStorage;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

public class UserControllerTest {
    private UserController controller;
    private UserStorage storage;

    private  FriendStorage friendDbStorage;
    private UserService service;
    private User user;

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @BeforeEach
    public void createUserAndController() {
        friendDbStorage = new FriendDbStorage(jdbcTemplate);
        storage = new UserDbStorage(jdbcTemplate);
        service = new UserService(storage,friendDbStorage);
        controller = new UserController(service);
        user = new User();
        user.setEmail("sviridovsa36reg@mail.ru");
        user.setLogin("Serg");
        user.setName("Sergey");
        LocalDate localDate = LocalDate.of(1985, 5, 27);
        user.setBirthday(localDate);
    }

    @Test
    public void shouldValidateUserOk() {
        service.validateUser(user);

        Assertions.assertEquals("Sergey", user.getName());
    }

    @Test
    public void shouldValidateUserNameNull() {
        user.setName(null);
        service.validateUser(user);

        Assertions.assertEquals("Serg", user.getName());
    }

    @Test
    public void shouldValidateUserNameBlank() {
        user.setName(" ");
        service.validateUser(user);

        Assertions.assertEquals("Serg", user.getName());
    }
}
