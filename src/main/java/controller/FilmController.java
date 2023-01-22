package controller;

import model.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    private final Map<String, Film> filmMap = new HashMap<>();

    @PostMapping("/film")
    public Film createFilm(@RequestBody Film film) {

        return film;
    }

    @PutMapping("/film")
    public Film updateFilm(@RequestBody Film film) {

        return filmMap.get(film.getReleaseDate());
    }

    @GetMapping("/films")
    Collection<Film> getFilms() {
        log.debug("Фильмов в каталоге: {}", filmMap.size());
        return filmMap.values();
    }
}
