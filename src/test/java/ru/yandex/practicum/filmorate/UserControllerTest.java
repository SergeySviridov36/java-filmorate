package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

public class UserControllerTest {
    private UserController controller;
    private UserStorage storage;
    private UserService service;
    private User user;
    @BeforeEach
    public void createUserAndController() {
        storage = new InMemoryUserStorage();
        service = new UserService(storage);
        controller = new UserController(service);
        user = new User(userRows.getLong("users_id"), userRows.getLong("email"), userRows.getLong("login"), userRows.getLong("name"), userRows.getLong("birthday"));
        user.setEmail("sviridovsa36reg@mail.ru");
        user.setLogin("Serg");
        user.setName("Sergey");
        LocalDate localDate = LocalDate.of(1985, 5, 27);
        user.setBirthday(localDate);
    }

    @Test
    public void shouldValidateUserOk() {
        service.validateUser(user);

        Assertions.assertEquals("Sergey",user.getName());
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
