package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;


import ru.yandex.practicum.filmorate.exceptions.InputDataException;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends Controller<Film> {
    private final LocalDate theBirthOfCinema = LocalDate.of(1895, 12, 28);

    @Override
    public void validate(Film film) {
        if (film.getReleaseDate().isBefore(theBirthOfCinema)) {
            throw new InputDataException("The release date must be - no earlier than December 28, 1895");
        }
        log.debug(film.getName() + " passed the test for releaseDate");
        log.debug(film.getName() + " passed the checks of all fields");
    }
}