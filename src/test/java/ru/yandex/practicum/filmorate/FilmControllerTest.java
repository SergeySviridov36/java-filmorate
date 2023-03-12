package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.yandex.practicum.filmorate.service.FilmService.data;

public class FilmControllerTest {

    private FilmController controller;
    private Film film;
    private FilmStorage storage;
    private FilmService service;
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @BeforeEach
    public void createFilmAndController() {
        storage = new FilmDbStorage(jdbcTemplate);
        service = new FilmService(storage);
        controller = new FilmController(service);
        film = new Film();
        film.setName("The Machinist");
        film.setDescription("thriller");
        LocalDate localDate = LocalDate.of(2004, 2, 9);
        film.setReleaseDate(localDate);
        film.setDuration(45);
    }

    @Test
    public void shouldValidateFilmOk() {
        service.validateFilm(film);
        film.setReleaseDate(data);
        service.validateFilm(film);
    }

    @Test
    public void shouldValidateFilmFail() {
        LocalDate localDate = LocalDate.of(1800, 12, 20);
        film.setReleaseDate(localDate);
        Exception exception = assertThrows(ValidationException.class, () -> service.validateFilm(film));

        Assertions.assertEquals("Ошибка валидации getReleaseDate", exception.getMessage());
    }
}
