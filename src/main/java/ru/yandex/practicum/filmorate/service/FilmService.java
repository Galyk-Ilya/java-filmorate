package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private static final LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);

    public Film createFilm(Film film) {
        filmValidation(film);
        return filmStorage.createFilm(film);
    }

    public List<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public Film update(Film film) {
        filmValidation(film);
        return filmStorage.update(film);
    }

    public Film getFilmById(int filmId) {
        return filmStorage.getFilmById(filmId);
    }

    public Film deleteFilm(int id) {
        return filmStorage.deleteFilm(id);
    }

    public void addLike(int filmId, int userId) {
        filmStorage.addLike(filmId, userId);
        log.info("movie id:" + filmId + " was liked by person id:" + userId);
    }

    public void deleteLike(int filmId, int userId) {
        filmStorage.deleteLike(filmId, userId);
        log.info("movie id:" + filmId + " was deleted by person id:" + userId);
    }

    public List<Film> getMostPopularFilms(int count) {
        return filmStorage.getPopularFilms(count);
    }

    public void filmValidation(Film film) {
        if (film.getReleaseDate().isBefore(CINEMA_BIRTHDAY)) {
            throw new ValidationException("The release date must be - no earlier than December 28, 1895");
        }
    }
}