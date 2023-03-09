package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FilmService {

    private final FilmDbStorage filmDbStorage;

    public FilmService(FilmDbStorage filmDbStorage) {
        this.filmDbStorage = filmDbStorage;
    }

    public static final LocalDate data = LocalDate.of(1895, 12, 28);

    public void createLikes(long idFilm, long idUser) {
        validateId(idFilm,idUser);
        filmDbStorage.createLikes(idFilm,idUser);

    }

    public void deleteLikes(long idUser, long idFilm) {
        validateId(idUser, idFilm);
        filmDbStorage.deleteLikesToFilm(idUser,idFilm);
    }

    public List<Film> getTenPopularFilm(int count) {
        validated(count);
        return filmDbStorage.getListFilms().stream().
                sorted(Comparator.comparingInt(Film::getRate).
                        reversed()).
                limit(count).
                collect(Collectors.toList());
    }

    public List<Film> getListFilms() {
        return filmDbStorage.getListFilms();
    }

    public Film getThisFilmById(long idFilm) {
        return filmDbStorage.getFilmById(idFilm);
    }

    public Film createFilm(Film film) {
        validateFilm(film);
        return filmDbStorage.save(film);
    }

    public Film updateFilm(Film film) throws ValidationException {
        validateFilm(film);
        return filmDbStorage.update(film);
    }

    public List<Genre> getListGenres() {
        return filmDbStorage.getListGenres();
    }

    public Genre getGenreById(Integer id) {
        return filmDbStorage.getGenreById(id);
    }

    public List<MPA> getListMPA() {
        return filmDbStorage.getListMPA();
    }

    public MPA getMPAById(Integer id) {
        return filmDbStorage.getMPAById(id);
    }

    private void validateId(long idUser, long idFilm) throws NotFoundException {
        if (idUser <= 0 || idFilm <= 0) {
            throw new NotFoundException("Ошибка! id не может быть меньше или равен 0.");
        }
    }

    public void validateFilm(Film film) {
        if (film.getReleaseDate().isBefore(data)) {
            throw new ValidationException("Ошибка валидации getReleaseDate");
        }
    }
    public  void validated(int id) {
        if (id <= 0) {
            throw new NotFoundException("Ошибка! id не может быть меньше или равен 0.");
        }
    }
}



