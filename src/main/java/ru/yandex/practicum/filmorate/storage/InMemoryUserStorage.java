package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private long userId;

    private final Map<Long, User> usersMap = new HashMap<>();

    @Override
    public void generatorId(User user) {
        ++userId;
        user.setId(userId);
    }

    @Override
    public User save(User user) {

        generatorId(user);
        usersMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) throws NotFoundException {
        if (usersMap.get(user.getId()) == null) {
            throw new NotFoundException("Пользователь для обновления не найден");
        }
        usersMap.put(user.getId(), user);
        return user;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(usersMap.values());
    }

    public User getThisUser(long id) throws NotFoundException {
        if (usersMap.get(id) == null) {
            throw new NotFoundException("Ошибка! Пользователь с таким id не найден.");
        }
        return usersMap.get(id);
    }
}
