package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

public interface UserStorage {

    void generatorId(User user);

    void save(User user);

    void update(User user);

    ArrayList<User> getAllUsers();
}
