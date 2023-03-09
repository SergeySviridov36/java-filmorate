package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j

@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) {
        filmService.createFilm(film);
        log.debug("Добавлен фильм: {}", film);
        return film;
    }

    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) {
        filmService.updateFilm(film);
        log.debug("Обновлен фильм: {}", film);
        return film;
    }

    @GetMapping("/films/{id}")
    public Film getFilmsById(@PathVariable long id) {
        log.debug("Информация о фильме по id: {}",id);
        return filmService.getThisFilmById(id);
    }

    @GetMapping("/films")
    public List<Film> getFilms() {
        log.debug("Поучен список фильмов.");
        return filmService.getListFilms();
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLikesToFilm(@PathVariable long id, @PathVariable long userId) {
        filmService.createLikes(id, userId);
        log.debug("Добавлен лайк к фильму: {}",id);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLikesToFilm(@PathVariable long id, @PathVariable long userId) {
        filmService.deleteLikes(userId, id);
        log.debug("Удален лайк к фильму: {}",id);
    }

    @GetMapping("/films/popular")
    public List<Film> getListCountFilms(@RequestParam(value = "count", defaultValue = "10", required = false) int count) {
        log.debug("Получен список фильмов по популярности.");
        return filmService.getTenPopularFilm(count);
    }

    @GetMapping("/genres")
    public List<Genre> getListGenres(){
        log.debug("Получен список жанров");
        return filmService.getListGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenreById (@PathVariable Integer id){
        log.debug("Информация о жанрах по id: {}",id);
        return filmService.getGenreById(id);
    }

    @GetMapping("/mpa")
    public List<MPA> getListMPA(){
        log.debug("Получен список жанров");
        return filmService.getListMPA();
    }

    @GetMapping("/mpa/{id}")
    public MPA getMPAById (@PathVariable Integer id){
        log.debug("Информация о жанрах по id: {}",id);
        return filmService.getMPAById(id);
    }

}
