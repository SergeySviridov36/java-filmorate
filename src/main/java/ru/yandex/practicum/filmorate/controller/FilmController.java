package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        filmService.createFilm(film);
        log.debug("Добавлен фильм: {}", film.getName());
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        filmService.updateFilm(film);
        log.debug("Обновлен фильм: {}", film.getName());
        return film;
    }

    @GetMapping("/{id}")
    public Film getFilmsById(@PathVariable long id) {
        log.debug("Информация о фильме по id: {}", filmService.getThisFilmById(id));
        return filmService.getThisFilmById(id);
    }

    @GetMapping
    public ArrayList<Film> getFilms() {
        log.debug("Фильмов в каталоге: {}", filmService.getListFilms().size());
        return new ArrayList<>(filmService.getListFilms());
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikesToFilm(@PathVariable long id, @PathVariable long userId) {
        filmService.createLikes(userId, id);
        log.debug("Добавлен лайк к фильму: {}", filmService.getThisFilmById(id));
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLikesToFilm(@PathVariable long id, @PathVariable long userId) {
        filmService.deleteLikes(userId, id);
        log.debug("Удален лайк к фильму: {}", filmService.getThisFilmById(id));
    }

    @GetMapping("/popular")
    public List<Film> getListCountFilms(@RequestParam(value = "count", defaultValue = "10", required = false) int count) {
        log.debug("Фильмов показано по лайкам : {}", filmService.getTenPopularFilm(count).size());
        return filmService.getTenPopularFilm(count);
    }
}
