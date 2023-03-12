package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {

    private final UserStorage userStorage;
    private final FilmStorage filmStorage;
    private User user;

    @BeforeEach
    public void createUser() {

        user = new User();
        user.setEmail("Serg@mail.ru");
        user.setLogin("Serg");
        user.setName("Sergey");
        user.setBirthday(LocalDate.of(1985, 5, 27));

    }

    @Test
    public void filmStorageTest() {

        Film film = new Film();
        film.setName("Rembo");
        film.setDescription("crazy men");
        film.setReleaseDate(LocalDate.of(1987, 11, 12));
        film.setDuration(115);
        MPA mpa = new MPA();
        mpa.setId(1);
        film.setMPA(mpa);
        Genre genre = new Genre();
        genre.setId(1);
        List<Genre> genres = List.of(genre);
        film.setGenres(genres);

        Film film0 = filmStorage.save(film);
        Assertions.assertEquals(film0.getId(), 1L);

        User userFilm = userStorage.save(user);
        filmStorage.createLikes(film0.getId(), userFilm.getId());

        Film film1 = filmStorage.getFilmById(1L);
        assertThat(film1).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(film1).hasFieldOrPropertyWithValue("rate", 1);

        filmStorage.deleteLikesToFilm(film0.getId(), user.getId());
        Film film2 = filmStorage.getFilmById(1L);
        assertThat(film2).hasFieldOrPropertyWithValue("rate", 0);

        film0.setName("frog");
        Film film3 = filmStorage.update(film0);
        assertThat(film3).hasFieldOrPropertyWithValue("name", "frog");

        List<Film> films = filmStorage.getListFilms();
        Assertions.assertEquals(1, films.size());
    }

    @Test
    public void userStorageTest() {

        User user1 = userStorage.save(user);
        assertThat(user1).hasFieldOrPropertyWithValue("id", 2L);

        User user2 = userStorage.findUserById(1L);
        assertThat(user2).hasFieldOrPropertyWithValue("id", 1L);

        user2.setName("Maxim");
        User user3 = userStorage.update(user2);
        assertThat(user3).hasFieldOrPropertyWithValue("name", "Maxim");

        List<User> users = userStorage.getAllUsers();
        Assertions.assertEquals(2, users.size());

        User friend = new User();
        friend.setEmail("Tom@mail.ru");
        friend.setLogin("Tom");
        friend.setName("Tomas");
        friend.setBirthday(LocalDate.of(2000, 2, 22));

        User friend1 = userStorage.save(friend);
        userStorage.createFriends(user3.getId(), friend1.getId());
        List<User> friends = userStorage.getFriends(user3.getId());
        Assertions.assertEquals(1, friends.size());

        userStorage.deleteFriends(user3.getId(), friend1.getId());
        List<User> friends1 = userStorage.getFriends(user3.getId());
        Assertions.assertEquals(0, friends1.size());
    }

    @Test
    public void genreTest() {
        List<Genre> genres = filmStorage.getListGenres();
        Assertions.assertEquals(6, genres.size());

        Genre genre = filmStorage.getGenreById(6);
        assertThat(genre).hasFieldOrPropertyWithValue("name", "Боевик");
    }

    @Test
    public void MPATest() {
        List<MPA> listMPA = filmStorage.getListMPA();
        Assertions.assertEquals(5, listMPA.size());

        MPA mpa = filmStorage.getMPAById(4);
        assertThat(mpa).hasFieldOrPropertyWithValue("name", "R");
    }
}