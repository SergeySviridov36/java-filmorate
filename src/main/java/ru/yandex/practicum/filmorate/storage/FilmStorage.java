package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.ArrayList;
import java.util.List;

public interface FilmStorage {

    Film save(Film film);

    Film update(Film film);

    List<Film> getListFilms();

    Film getFilmById(long id);

    List<Genre> getListGenre();

    Genre getGenreById(Integer id);

    List<MPA> getListMPA();

    MPA getMPAById (Integer mpaId);

}
