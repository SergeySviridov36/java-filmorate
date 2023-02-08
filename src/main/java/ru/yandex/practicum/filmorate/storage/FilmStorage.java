package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {

    void generatorId(Film film);

    void save(Film film);

    void update(Film film);

    ArrayList<Film> getAllFilms();
}
