package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getListGenres() {
        String sql = "SELECT * FROM genre";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mappGenre(rs));
    }

    @Override
    public Genre getGenreById(Integer id) {
        String sql = "SELECT * FROM genre WHERE genre_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mappGenre(rs), id).
                stream().findAny().orElseThrow(() -> new NotFoundException("Жанр с id " + id + " не найден."));
    }

    private Genre mappGenre(ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        genre.setId(rs.getInt("genre_id"));
        genre.setName(rs.getString("genre_name"));
        return genre;
    }
}
