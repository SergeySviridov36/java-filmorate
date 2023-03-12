package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO USERS(LOGIN,NAME,EMAIL,BIRTHDAY) VALUES(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"users_id"});
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE USERS SET EMAIL= ?, LOGIN =?, NAME = ?, BIRTHDAY =? WHERE USERS_ID =? ";
        int upd = jdbcTemplate.update(sql, user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        if (upd == 0) {
            throw new NotFoundException("Пользователь для обновления не найден");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM USERS";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapperUser(rs));
    }

    @Override
    public User findUserById(Long id) throws NotFoundException {
        String sql = "select * from users where users_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapperUser(rs), id).
                stream().findAny().orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден."));
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


