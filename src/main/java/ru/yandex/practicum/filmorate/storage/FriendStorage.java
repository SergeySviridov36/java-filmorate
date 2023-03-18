package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendStorage {
    List<User> getFriends(long idUser);

    void createFriends(long idUser, long idFriend);

    void deleteFriends(long idUser, long idFriend);
}
