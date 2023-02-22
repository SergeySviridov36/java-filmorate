package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FilmService {

    private final FilmStorage filmStorage;

    public static final LocalDate data = LocalDate.of(1895, 12, 28);

    public void createLikes(long idUser, long idFilm) {
        validateId(idUser, idFilm);
        filmStorage.getThisFilm(idFilm).getLikes().add(idUser);

    }

    public void deleteLikes(long idUser, long idFilm) {
        validateId(idUser, idFilm);
        filmStorage.getThisFilm(idFilm).getLikes().remove(idUser);
    }

    public List<Film> getTenPopularFilm(int count) {
        return filmStorage.getAllFilms().stream().
                sorted((f0, f1) -> {
                    int comp = getByLikes(f0).compareTo(getByLikes(f1));
                    comp = -1 * comp;
                    return comp;
                }).limit(count).collect(Collectors.toList());
    }

    public List<Film> getListFilms() {
        return filmStorage.getAllFilms();
    }

    public Film getThisFilmById(long idFilm) {
        return filmStorage.getThisFilm(idFilm);
    }

    public Film createFilm(Film film) throws ValidationException {
        validateFilm(film);
        return filmStorage.save(film);
    }

    public Film updateFilm(Film film) throws ValidationException {
        validateFilm(film);
        return filmStorage.update(film);
    }

    private void validateId(long idUser, long idFilm) throws NotFoundException {
        if (idUser <= 0 || idFilm <= 0) {
            throw new NotFoundException("Ошибка! id не может быть меньше или равен 0.");
        }
    }

    public void validateFilm(Film film){
        if (film.getReleaseDate().isBefore(data)) {
            throw new ValidationException("Ошибка валидации getReleaseDate");
        }
    }

    private Integer getByLikes(Film film) {
        return film.getLikes().size();
    }
}
