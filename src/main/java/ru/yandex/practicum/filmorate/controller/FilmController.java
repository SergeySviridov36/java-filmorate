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

    private long idFilm;
    private final LocalDate data = LocalDate.of(1895, 12, 28);
    private final Map<Long, Film> filmMap = new HashMap<>();

    private void generatorId(Film film){
        ++idFilm;
        film.setId(idFilm);
    }

    @PostMapping
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) {

            if (film.getReleaseDate().isBefore(data)) {
                log.debug("Ошибка валидации getReleaseDate! : {}",film.getReleaseDate());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(film);
            }
            generatorId(film);
            filmMap.put(film.getId(), film);
            log.debug("Добавлен фильм: {}", film.getName());
            return ResponseEntity.ok(film);
        }


    @PutMapping
    public ResponseEntity<Film> update(@Valid @RequestBody Film film) {

            if (film.getId() == 0 || film.getReleaseDate().isBefore(data) ||
                    !filmMap.containsKey(film.getId())) {
                log.debug("Ошибка валидации update Film!: {}, {}, {}",film.getId(),film.getReleaseDate(),data);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(film);
            }
            filmMap.put(film.getId(), film);
            log.debug("Обновлен фильм: {}", film.getName());
            return ResponseEntity.ok(film);

    }

    @GetMapping
    public ResponseEntity<ArrayList<Film>> getFilms() {
        log.debug("Фильмов в каталоге: {}", filmMap.size());
        return ResponseEntity.ok(new ArrayList<>(filmMap.values()));
    }
}
