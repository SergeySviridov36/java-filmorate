package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage{

    private long userId;
    @Getter
    @Setter
    private final Map<Long, User> usersMap = new HashMap<>();

    @Override
    public void generatorId(User user) {
        ++userId;
        user.setId(userId);
    }

    @Override
    public void save(User user) {
        generatorId(user);
        usersMap.put(user.getId(), user);
    }

    @Override
    public void update(User user) {
        user.setName(user.getLogin());
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(usersMap.values());
    }
}
