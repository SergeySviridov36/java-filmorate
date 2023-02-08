package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements  FilmStorage{

    private long idFilm;
    @Getter
    @Setter
    private final Map<Long, Film> filmMap = new HashMap<>();

    @Override
    public void generatorId(Film film){
        ++idFilm;
        film.setId(idFilm);
    }

    @Override
    public void save(Film film) {
        generatorId(film);
        filmMap.put(film.getId(), film);
    }

    @Override
    public void update(Film film) {
        filmMap.put(film.getId(), film);
    }

    @Override
    public ArrayList<Film> getAllFilms() {
        return new ArrayList<>(filmMap.values());
    }
}
