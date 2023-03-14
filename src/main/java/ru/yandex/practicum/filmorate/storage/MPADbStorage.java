package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MPADbStorage implements MPAStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MPADbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MPA> getListMPA() {
        String sql = "SELECT * FROM film_rating";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mappMPA(rs));
    }

    @Override
    public MPA getMPAById(Integer mpaId) {
        String sql = "SELECT * FROM film_rating WHERE MPA_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mappMPA(rs), mpaId).
                stream().findAny().orElseThrow(() -> new NotFoundException("Рейтинг с id " + mpaId + " не найден."));
    }

    private MPA mappMPA(ResultSet rs) throws SQLException {
        MPA mpa = new MPA();
        mpa.setId(rs.getInt("MPA_id"));
        mpa.setName(rs.getString("MPA_name"));
        return mpa;
    }
}
