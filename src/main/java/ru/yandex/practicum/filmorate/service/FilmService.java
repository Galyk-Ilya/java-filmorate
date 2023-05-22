package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;

    private final GenreStorage genreDbStorage;

    private final MpaStorage mpaDbStorage;

    private static final LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);

    public Film createFilm(Film film) {
        validateDateFilm(film);
        return filmStorage.createFilm(film);
    }

    public List<Film> findAllFilms() {
        List<Film> films = filmStorage.findAllFilms();
        films.forEach(f -> {
            f.setGenres(genreDbStorage.getByFilmId(f.getId()));
            f.setMpa(mpaDbStorage.getByFilmId(f.getId()));
        });
        return films;
    }

    public Film update(Film film) {
        validateDateFilm(film);
        containsFilm(film.getId());
        Film resultFilm = filmStorage.update(film);
        resultFilm.setGenres(genreDbStorage.getByFilmId(film.getId()));
        return resultFilm;
    }

    public Film findById(int filmId) {
        Film film = filmStorage.findById(filmId).orElseThrow(() -> new NotFoundException("Movie not found"));
        log.debug("Movie with id {} found.", filmId);
        film.setGenres(genreDbStorage.getByFilmId(filmId));
        film.setMpa(mpaDbStorage.getByFilmId(filmId));
        return film;
    }

    public void deleteFilm(int id) {
        containsFilm(id);
        filmStorage.deleteFilm(id);
    }

    public void addLike(int filmId, int userId) {
        containsFilm(filmId);
        containsUser(userId);
        filmStorage.addLike(filmId, userId);
        log.info("movie id:" + filmId + " was liked by person id:" + userId);
    }

    public void deleteLike(int filmId, int userId) {
        containsFilm(filmId);
        containsUser(userId);
        filmStorage.deleteLike(filmId, userId);
        log.info("movie id:" + filmId + " was deleted by person id:" + userId);
    }

    public List<Film> getMostPopularFilms(int count) {
        List<Film> films = filmStorage.getPopularFilms(count);
        films.forEach(f -> {
            f.setGenres(genreDbStorage.getByFilmId(f.getId()));
            f.setMpa(mpaDbStorage.getByFilmId(f.getId()));
        });
        return films;
    }

    private void validateDateFilm(Film film) {
        if (film.getReleaseDate().isBefore(CINEMA_BIRTHDAY)) {
            throw new ValidationException("The release date must be - no earlier than December 28, 1895");
        }
    }

    private void containsFilm(int id) {
        filmStorage.findById(id).orElseThrow(() -> new NotFoundException("Movie not found"));
    }

    private void containsUser(int id) {
        filmStorage.findUserById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }
}