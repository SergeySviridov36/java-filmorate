package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User save(User user);

    User update(User user);

    List<User> getAllUsers();

    User findUserById(Long id) ;

    List<User> getFriends(long idUser);

    void createFriends(long idUser, long idFriend);

    void deleteFriends(long idUser, long idFriend);
}
