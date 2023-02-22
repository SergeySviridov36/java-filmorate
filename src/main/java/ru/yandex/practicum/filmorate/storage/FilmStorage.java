package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {

    void generatorId(Film film);

    Film save(Film film);

    Film update(Film film);

    ArrayList<Film> getAllFilms();

    Film getThisFilm(long id);

}
