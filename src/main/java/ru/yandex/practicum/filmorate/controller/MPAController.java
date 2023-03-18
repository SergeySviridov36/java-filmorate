package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MPAController {

    private final FilmService filmService;

    @GetMapping("/mpa")
    public List<MPA> getListMPA() {
        log.debug("Получен список жанров");
        return filmService.getListMPA();
    }

    @GetMapping("/mpa/{id}")
    public MPA getMPAById(@PathVariable Integer id) {
        log.debug("Информация о жанрах по id: {}", id);
        return filmService.getMPAById(id);
    }
}
