package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getFriends(long idUser) {
        String sql = "select * from users where users_id in (select friend_id from friend_user where users_id = ?)";
        return jdbcTemplate.query(sql, ((rs, rowNum) -> mapperUser(rs)), idUser);
    }

    @Override
    public void createFriends(long idUser, long idFriend) {
        String sql = "INSERT INTO friend_user(users_id,friend_id) VALUES (?,?) ";
        jdbcTemplate.update(sql, idUser, idFriend);
    }

    @Override
    public void deleteFriends(long idUser, long idFriend) {
        String sql = "DELETE FROM friend_user WHERE users_id=? and friend_id=?";
        jdbcTemplate.update(sql, idUser, idFriend);
    }

    private User mapperUser(ResultSet resultSet) throws SQLException {

        User user = new User();
        user.setId(resultSet.getLong("users_id"));
        user.setEmail(resultSet.getString("email"));
        user.setLogin(resultSet.getString("login"));
        user.setName(resultSet.getString("name"));
        user.setBirthday(resultSet.getDate("birthday").toLocalDate());
        return user;
    }
}
