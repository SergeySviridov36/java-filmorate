package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
@Validated
public class FilmController {

    private int createid;
    private final LocalDate data = LocalDate.of(1895, 12, 28);

    private final Map<Integer, Film> filmMap = new HashMap<>();

    @PostMapping
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) {
        try {
            if (film.getReleaseDate().isBefore(data)) {
                throw new ValidationException("Ошибка данных.");
            }
            ++createid;
            film.setId(createid);
            filmMap.put(film.getId(), film);
            log.debug("Добавлен фильм: {}", film.getName());
            return ResponseEntity.ok(film);
        } catch (ValidationException exception) {
            log.debug(exception.getMessage(),exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(film);
        }
    }

    @PutMapping
    public ResponseEntity<Film> update(@Valid @RequestBody Film film) {
        try {
            if (film.getId() == 0 || film.getReleaseDate().isBefore(data) ||
                    !filmMap.containsKey(film.getId())) {
                throw new ValidationException("Ошибка данных.");
            }
            filmMap.put(film.getId(), film);
            log.debug("Обновлен фильм: {}", film.getName());
            return ResponseEntity.ok(film);
        } catch (ValidationException exception) {
            log.debug(exception.getMessage(),exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(film);
        }
    }

    @GetMapping
    public ResponseEntity<ArrayList<Film>> getFilms() {
        log.debug("Фильмов в каталоге: {}", filmMap.size());
        return ResponseEntity.ok(new ArrayList<>(filmMap.values()));
    }
}
