package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film save(Film film);

    Film update(Film film);

    List<Film> getListFilms();

    List<Film> getPopularFilm(int count);

    Film getFilmById(long id);

    void createLikes(long idFilm, long idUser);

    void deleteLikesToFilm(long idUser, long idFilm);
}
