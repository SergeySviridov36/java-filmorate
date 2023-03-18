package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserStorage userDbStorage;

    private final FriendStorage friendDbStorage;

    @Autowired
    public UserService(UserStorage userDbStorage, FriendStorage friendDbStorage) {
        this.userDbStorage = userDbStorage;
        this.friendDbStorage = friendDbStorage;
    }

    public User createUser(User user) {
        validateUser(user);
        return userDbStorage.save(user);
    }

    public User updateUser(User user) throws ValidationException {
        validateUser(user);
        userDbStorage.update(user);
        return user;
    }

    public List<User> getListUsers() {
        return userDbStorage.getAllUsers();
    }

    public User findUserById(Long idUser) {
        return userDbStorage.findUserById(idUser);
    }

    public void createFriends(long idUser, long idFriend) {
        validateId(idUser, idFriend);
        friendDbStorage.createFriends(idUser, idFriend);

    }

    public void deleteFriends(long idUser, long idFriend) {
        validateId(idUser, idFriend);
        friendDbStorage.deleteFriends(idUser, idFriend);
    }

    public List<User> getFriends(long idUser) {
        validateId(idUser);
        return friendDbStorage.getFriends(idUser);
    }

    public List<User> commonFriends(long idUser, long idFriends) {
        validateId(idUser, idFriends);
        List<User> listFriendUser = friendDbStorage.getFriends(idUser);
        return friendDbStorage.getFriends(idFriends).stream().
                filter(listFriendUser::contains).
                collect(Collectors.toList());
    }

    private void validateId(long idUser, long idFriend) throws NotFoundException {
        if (idUser <= 0 || idFriend <= 0) {
            throw new NotFoundException("Ошибка! id не может быть меньше или равен 0.");
        }
    }

    private void validateId(long idUser) throws NotFoundException {
        if (idUser <= 0) {
            throw new NotFoundException("Ошибка! id не может быть меньше или равен 0.");
        }
    }

    public void validateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
