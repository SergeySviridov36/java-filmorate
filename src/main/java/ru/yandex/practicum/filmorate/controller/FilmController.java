package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.debug("Добавлен фильм: {}", film);
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.debug("Обновлен фильм: {}", film);
        return filmService.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilmsById(@PathVariable long id) {
        log.debug("Информация о фильме по id: {}", id);
        return filmService.getThisFilmById(id);
    }

    @GetMapping
    public List<Film> getFilms() {
        log.debug("Поучен список фильмов.");
        return filmService.getListFilms();
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikesToFilm(@PathVariable long id, @PathVariable long userId) {
        filmService.createLikes(id, userId);
        log.debug("Добавлен лайк к фильму: {}", id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLikesToFilm(@PathVariable long id, @PathVariable long userId) {
        filmService.deleteLikes(userId, id);
        log.debug("Удален лайк к фильму: {}", id);
    }

    @GetMapping("/popular")
    public List<Film> getListCountFilms(@RequestParam(value = "count", defaultValue = "10", required = false) int count) {
        log.debug("Получен список фильмов по популярности.");
        return filmService.getTenPopularFilm(count);
    }
}
