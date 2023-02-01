package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmControllerTest {

    private FilmController filmController;
    private Film film;
    static LocalDate localDate;

    @BeforeEach
    public void createForAllTestUserControllerAndUser() {
        filmController = new FilmController();
        film = new Film();
        film.setName("Халк");
        film.setDescription("Доктор Бэннер подвергся воздействию гамма-лучей и превратился в Халка " +
                "- существо невероятной физической силы");
        LocalDate localData = LocalDate.of(2003, 6, 17);
        film.setReleaseDate(localData);
        film.setDuration(138);
    }

    @Test
    public void shouldIdFilPost() {
        ResponseEntity<Film> responseEntity = filmController.create(film);
        Assertions.assertEquals(1, responseEntity.getBody().getId());
    }

    @Test
    public void shouldIdFilmPut() {
        ResponseEntity<Film> responseEntity1 = filmController.create(film);
        ResponseEntity<Film> responseEntity2 = filmController.update(film);
        int cod1 = responseEntity2.getStatusCodeValue();

        Assertions.assertEquals(200, cod1);

        film.setId(333);
        ResponseEntity<Film> responseEntity3 = filmController.update(film);
        int cod2 = responseEntity3.getStatusCodeValue();
        Assertions.assertEquals(500, cod2);

        film.setId(0);
        ResponseEntity<Film> responseEntity4 = filmController.update(film);
        int cod3 = responseEntity4.getStatusCodeValue();
        Assertions.assertEquals(500, cod3);

    }

    @Test
    public void shouldReleaseDateFilmPost() {
        ResponseEntity<Film> responseEntity = filmController.create(film);
        int cod = responseEntity.getStatusCodeValue();
        Assertions.assertEquals(200, cod);
    }

    @Test
    public void shouldBeforeReleaseDatePostFilm() {

        localDate = LocalDate.of(1895, 12, 27);
        film.setReleaseDate(localDate);
        ResponseEntity<Film> responseEntity = filmController.create(film);
        int cod = responseEntity.getStatusCodeValue();
        Assertions.assertEquals(500, cod);

    }

    @Test
    public void shouldBoundaryValuePostFilm() {

        localDate = LocalDate.of(1895, 12, 28);
        film.setReleaseDate(localDate);
        ResponseEntity<Film> responseEntity = filmController.create(film);
        int cod = responseEntity.getStatusCodeValue();
        Assertions.assertEquals(200, cod);

    }

    @Test
    public void shouldUpdateNorm() {
        ResponseEntity<Film> responseEntity = filmController.create(film);

        Film film1 = new Film();
        film1.setId(1);
        film1.setName("Халк 2");
        film1.setDescription("Доктор Бэннер подвергся воздействию гамма-лучей и превратился в Халка " +
                "- существо невероятной физической силы");
        LocalDate localData1 = LocalDate.of(2012, 12, 12);
        film1.setReleaseDate(localData1);
        film1.setDuration(138);

        ResponseEntity<Film> responseEntity2 = filmController.update(film1);
        int cod = responseEntity2.getStatusCodeValue();
        Assertions.assertEquals(200, cod);

    }

    @Test
    public void shouldUpdateData27() {
        ResponseEntity<Film> responseEntity = filmController.create(film);

        Film film2 = new Film();
        film2.setId(1);
        film2.setName("Халк 3");
        film2.setDescription("Доктор Бэннер подвергся воздействию гамма-лучей и превратился в Халка " +
                "- существо невероятной физической силы");
        LocalDate localData2 = LocalDate.of(1895, 12, 27);
        film2.setReleaseDate(localData2);
        film2.setDuration(138);

        ResponseEntity<Film> responseEntity3 = filmController.update(film2);
        int cod2 = responseEntity3.getStatusCodeValue();
        Assertions.assertEquals(500, cod2);

    }

    @Test
    public void shouldUpdateData28() {
        ResponseEntity<Film> responseEntity = filmController.create(film);

        Film film3 = new Film();
        film3.setId(1);
        film3.setName("Халк 4");
        film3.setDescription("Доктор Бэннер подвергся воздействию гамма-лучей и превратился в Халка " +
                "- существо невероятной физической силы");
        LocalDate localData3 = LocalDate.of(1895, 12, 28);
        film3.setReleaseDate(localData3);
        film3.setDuration(138);

        ResponseEntity<Film> responseEntity3 = filmController.update(film3);
        int cod3 = responseEntity3.getStatusCodeValue();
        Assertions.assertEquals(200, cod3);

    }
}
