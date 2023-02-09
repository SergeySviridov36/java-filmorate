package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private long idFilm;

    private final Map<Long, Film> filmMap = new HashMap<>();

    @Override
    public void generatorId(Film film) {
        ++idFilm;
        film.setId(idFilm);
    }

    @Override
    public Film save(Film film) {
        generatorId(film);
        filmMap.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) throws NotFoundException {
        if (filmMap.get(film.getId()) == null) {
            throw new NotFoundException("Ошибка! Фильм для обновления не найден.");
        }
        filmMap.put(film.getId(), film);
        return film;
    }

    @Override
    public ArrayList<Film> getAllFilms() {
        return new ArrayList<>(filmMap.values());
    }

    @Override
    public Film getThisFilm(long id) throws NotFoundException {
        if (filmMap.get(id) == null) {
            throw new NotFoundException("Ошибка! Не найден фильм по данному id.");
        }
        return filmMap.get(id);
    }
}
