package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private static final LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);

    public Film createFilm(Film film) {
        validateDateFilm(film);
        return filmStorage.createFilm(film);
    }

    public List<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public Film update(Film film) {
        containsFilm(film.getId());
        validateDateFilm(film);
        return filmStorage.update(film);
    }

    public Film findById(int filmId) {
        Optional<Film> film = filmStorage.findById(filmId);
        if (film.isPresent()) {
            log.warn("Movie with id {} found.", filmId);
            return film.get();
        } else {
            log.warn("Movie with id {} was not found.", filmId);
            throw new NotFoundException("Movie not found");
        }
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
        return filmStorage.getPopularFilms(count);
    }

    public void validateDateFilm(Film film) {
        if (film.getReleaseDate().isBefore(CINEMA_BIRTHDAY)) {
            throw new ValidationException("The release date must be - no earlier than December 28, 1895");
        }
    }

    private void containsFilm(int id) {
        if (filmStorage.findById(id).isEmpty()) {
            log.warn("Movie with id {} was not found.", id);
            throw new NotFoundException("Movie not found");
        }
    }

    private void containsUser(int id) {
        if (filmStorage.findUserById(id).isEmpty()) {
            log.warn("Movie with id {} was not found.", id);
            throw new NotFoundException("User not found");
        }
    }
}