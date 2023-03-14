package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film save(Film film) {
        String sql = "INSERT INTO film(film_name,description,release_date,duration,rate,MPA_id) VALUES(?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getRate());
            stmt.setInt(6, film.getMPA().getId());
            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().longValue());
        addFilmGenre(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        String sql = "UPDATE film SET film_name= ?, description =?, release_date = ?,duration =?," +
                "rate= ?,MPA_id=? WHERE film_id =? ";
        int upd = jdbcTemplate.update(sql, film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                film.getMPA().getId(),
                film.getId());
        if (upd == 0) {
            throw new NotFoundException("Фильм для обновления не найден");
        }
        addFilmGenre(film);
        return getFilmById(film.getId());
    }

    @Override
    public List<Film> getListFilms() {
        String sql = "SELECT *, MPA_name,g.genre_id, genre_name  FROM film f " +
                "LEFT JOIN film_rating AS fr ON f.MPA_id = fr.MPA_id " +
                "LEFT JOIN film_genre AS fg ON f.film_id = fg.film_id " +
                "LEFT JOIN genre AS  g ON g.genre_id = fg.genre_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapperFilms(rs));
    }

    @Override
    public List<Film> getPopularFilm(int count) {
        String sql = "SELECT *, fr.MPA_name " +
                "FROM film f " +
                "LEFT JOIN film_rating AS fr ON f.MPA_id = fr.MPA_id " +
                "ORDER BY rate DESC LIMIT ?";
        List<Film> j = jdbcTemplate.query(sql, (rs, rowNum) -> mapperFilms(rs), count);
        return j;
    }

    @Override
    public Film getFilmById(long id) {
        String sql = "SELECT *, fr.MPA_name " +
                "FROM film f " +
                "LEFT JOIN film_rating fr ON f.MPA_id = fr.MPA_id " +
                "WHERE film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapperFilms(rs), id).
                stream().findAny().orElseThrow(() -> new NotFoundException("Фильм с id " + id + " не найден."));

    }

    @Override
    public void createLikes(long idFilm, long idUser) {
        String sql1 = "INSERT INTO like_films(film_id,users_id) VALUES(?,?)";
        jdbcTemplate.update(sql1, idFilm, idUser);
        String sql2 = "UPDATE film SET rate = rate + 1  WHERE film_id=?";
        jdbcTemplate.update(sql2, idFilm);
    }

    @Override
    public void deleteLikesToFilm(long idFilm, long idUser) {
        String sql = "DELETE FROM like_films WHERE film_id=? and users_id= ?";
        jdbcTemplate.update(sql, idFilm, idUser);
        String sql2 = "UPDATE film SET rate = rate - 1 WHERE film_id=?";
        jdbcTemplate.update(sql2, idFilm);
    }

    private void addFilmGenre(Film film) {
        String sql1 = "DELETE film_genre WHERE film_id = ?";
        jdbcTemplate.update(sql1, film.getId());
        String sql2 = "INSERT INTO film_genre(genre_id,film_id) VALUES (?,?)";
        if (film.getGenres() == null || film.getGenres().isEmpty()) {
            return;
        }
        Set<Genre> genre = new HashSet<>(film.getGenres());
        ArrayList<Genre> genres = new ArrayList<>(genre);
        jdbcTemplate.batchUpdate(sql2, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, genres.get(i).getId());
                ps.setLong(2, film.getId());
            }

            public int getBatchSize() {
                return genres.size();
            }
        });
    }

    private List<Genre> getGenreByIdFilms(Long idFilm) {
        String sql = "SELECT g.genre_id, g.genre_name " +
                "FROM genre g " +
                "LEFT JOIN film_genre AS fg ON g.genre_id = fg.genre_id " +
                "WHERE film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mappGenre(rs), idFilm);
    }

    private Film mapperFilms(ResultSet rs) throws SQLException {

        Film film = new Film();
        film.setId(rs.getLong("film_id"));
        film.setName(rs.getString("film_name"));
        film.setReleaseDate(rs.getDate("release_date").toLocalDate());
        film.setDescription(rs.getString("description"));
        film.setDuration(rs.getInt("duration"));
        film.setRate(rs.getInt("rate"));
        MPA mpa = mappMPA(rs);
        film.setMPA(mpa);
        film.setGenres(getGenreByIdFilms(rs.getLong("film_id")));
        return film;
    }

    private MPA mappMPA(ResultSet rs) throws SQLException {
        MPA mpa = new MPA();
        mpa.setId(rs.getInt("MPA_id"));
        mpa.setName(rs.getString("MPA_name"));
        return mpa;
    }

    private Genre mappGenre(ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        genre.setId(rs.getInt("genre_id"));
        genre.setName(rs.getString("genre_name"));
        return genre;
    }
}


