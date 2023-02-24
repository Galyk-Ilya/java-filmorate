package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;


import ru.yandex.practicum.filmorate.exceptions.InputDataException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends Controller<Film> {
    private final LocalDate theBirthOfCinema = LocalDate.of(1895, 12, 28);

    public Collection<Film> getAll() {
        return super.getAll();
    }

    public Film create(@RequestBody Film film) {
        log.debug("create film");
        return super.create(film);
    }

    public Film update(@RequestBody Film film) {
        log.debug("update film");
        return super.update(film);
    }

    public void validate(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new InputDataException("Movie title cannot be empty");
        }
        log.debug(film.getName() + " passed the test for name");
        if (film.getDescription().length() > 200) {
            throw new InputDataException("The maximum description length is 200 characters");
        }
        log.debug(film.getName() + " passed the test for description");

        if (film.getReleaseDate().isBefore(theBirthOfCinema)) {
            throw new InputDataException("The release date must be - no earlier than December 28, 1895");
        }
        log.debug(film.getName() + " passed the test for releaseDate");
        if (Duration.ofMinutes(film.getDuration()).isNegative()) {
            throw new InputDataException("Movie duration must be positive");
        }
        log.debug(film.getName() + " passed the test for duration");
        log.debug(film.getName() + " passed the checks of all fields");
    }
}