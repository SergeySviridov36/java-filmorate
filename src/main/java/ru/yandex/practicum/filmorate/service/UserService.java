package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserStorage userStorage;

    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.save(user);
    }

    public User updateUser(User user) throws ValidationException {

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        userStorage.update(user);
        return user;
    }

    public List<User> getListUsers() {
        return userStorage.getAllUsers();
    }

    public User getThisUserById(Long idUser) {
        return userStorage.getThisUser(idUser);
    }

    public void createFriends(long idUser, long idFriend) {
        validateId(idUser, idFriend);
        userStorage.getThisUser(idUser).getFriends().add(idFriend);
        userStorage.getThisUser(idFriend).getFriends().add(idUser);

    }

    public User getThisFriend(Long idFriend) {
        return userStorage.getThisUser(idFriend);
    }

    public void deleteFriends(long idUser, long idFriend) {
        validateId(idUser, idFriend);
        userStorage.getThisUser(idUser).getFriends().remove(idFriend);
        userStorage.getThisUser(idFriend).getFriends().remove(idUser);
    }

    public List<User> getFriends(long idUser) {
        validateId(idUser);
        return userStorage.getThisUser(idUser).getFriends().stream().
                map(userStorage::getThisUser).
                collect(Collectors.toList());
    }

    public List<User> commonFriends(long idUser, long idFriends) {
        validateId(idUser, idFriends);
        Set<Long> listFriendUser = userStorage.getThisUser(idUser).getFriends();
        return userStorage.getThisUser(idFriends).getFriends().stream().
                filter(listFriendUser::contains).
                map(userStorage::getThisUser).
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
}
