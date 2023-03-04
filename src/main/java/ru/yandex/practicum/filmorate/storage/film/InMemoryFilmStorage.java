package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.InputDataException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.AbstractStorage;

import java.time.LocalDate;

@Slf4j
@Component
public class InMemoryFilmStorage extends AbstractStorage<Film> implements FilmStorage {
    private static final LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);

    protected void validate(Film entity) {
        if (entity.getReleaseDate().isBefore(CINEMA_BIRTHDAY)) {
            throw new InputDataException("The release date must be - no earlier than December 28, 1895");
        }
        log.debug(entity.getName() + " passed the test for releaseDate");
    }
}