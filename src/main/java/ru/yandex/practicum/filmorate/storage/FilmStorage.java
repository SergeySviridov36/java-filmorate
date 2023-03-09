package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

public interface FilmStorage {

    Film save(Film film);

    Film update(Film film);

    List<Film> getListFilms();

    Film getFilmById(long id);

    List<Genre> getListGenres();

    Genre getGenreById(Integer id);

    List<MPA> getListMPA();

    MPA getMPAById (Integer id);

    void createLikes(long idFilm,long idUser);

    void deleteLikesToFilm(long idUser, long idFilm);
}
