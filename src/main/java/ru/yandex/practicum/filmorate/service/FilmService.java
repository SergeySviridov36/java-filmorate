package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.MPAStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;


@Service
public class FilmService {
    private final MPAStorage MPADbStorage;

    private final GenreStorage genreDbStorage;

    private final FilmStorage filmDbStorage;

    @Autowired
    public FilmService(MPAStorage MPADbStorage, GenreStorage genreDbStorage, FilmStorage filmDbStorage) {
        this.MPADbStorage = MPADbStorage;
        this.genreDbStorage = genreDbStorage;
        this.filmDbStorage = filmDbStorage;
    }

    public static final LocalDate data = LocalDate.of(1895, 12, 28);

    public void createLikes(long idFilm, long idUser) {
        validateId(idFilm, idUser);
        filmDbStorage.createLikes(idFilm, idUser);

    }

    public void deleteLikes(long idUser, long idFilm) {
        validateId(idUser, idFilm);
        filmDbStorage.deleteLikesToFilm(idUser, idFilm);
    }

    public List<Film> getTenPopularFilm(int count) {
        validated(count);
        return filmDbStorage.getPopularFilm(count);
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
        return genreDbStorage.getListGenres();
    }

    public Genre getGenreById(Integer id) {
        return genreDbStorage.getGenreById(id);
    }

    public List<MPA> getListMPA() {
        return MPADbStorage.getListMPA();
    }

    public MPA getMPAById(Integer id) {
        return MPADbStorage.getMPAById(id);
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

    public void validated(int id) {
        if (id <= 0) {
            throw new NotFoundException("Ошибка! id не может быть меньше или равен 0.");
        }
    }
}



