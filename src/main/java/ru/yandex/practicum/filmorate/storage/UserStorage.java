package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

public interface UserStorage {

    void generatorId(User user);

    User save(User user);

    User update(User user);

    ArrayList<User> getAllUsers();

    User getThisUser(long id);
}
