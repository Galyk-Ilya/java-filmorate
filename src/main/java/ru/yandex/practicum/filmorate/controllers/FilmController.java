package ru.yandex.practicum.filmorate.controllers;

import com.google.gson.annotations.JsonAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;


import ru.yandex.practicum.filmorate.exceptions.InputDataException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@JsonAdapter(Film.class)
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends Controller<Film> {
    private final LocalDate theBirthOfCinema = LocalDate.of(1895, 12, 28);

    @Override
    @GetMapping
    public Collection<Film> getAll() {
        return super.getAll();
    }

    @Override
    @PostMapping
    public Film create(@RequestBody Film film) {
        log.debug("create film");
        return super.create(film);
    }

    @Override
    @PutMapping
    public Film update(@RequestBody Film film) {
        log.debug("update film");
        return super.update(film);
    }

    public void validate(Film film, boolean isCreate) {
        if (isCreate) {
            if (dataMap.containsKey(film.getId())) {
                throw new InputDataException("Невозможно добавить " + film.getName() + ", так как он был добавлен ранее");
            }
            log.debug("film " + film.getName() + " id" + film.getId() + " изменен");
        } else {
            if (!dataMap.containsKey(film.getId())) {
                throw new InputDataException("Невозможно обновить данные фильма, " + film.getName() + " не был добавлен ранее");
            }
            log.debug("film " + film.getName() + " id" + film.getId() + " изменен");
        }

        if (film.getName() == null || film.getName().isBlank()) {
            throw new InputDataException("Название фильма не может быть пустым");
        }
        log.debug(film.getName() + " прошел проверку на name");
        if (film.getDescription().length() > 200) {
            throw new InputDataException("Максимальная длина описания — 200 символов");
        }
        log.debug(film.getName() + " прошел проверку на description");

        if (film.getReleaseDate().isBefore(theBirthOfCinema)) {
            throw new InputDataException("Дата релиза должна быть — не раньше 28 декабря 1895 года");
        }
        log.debug(film.getName() + " прошел проверку на releaseDate");
        if (Duration.ofMinutes(film.getDuration()).isNegative()) {
            throw new InputDataException("Продолжительность фильма должна быть положительной");
        }
        log.debug(film.getName() + " прошел проверку на duration");
        log.debug(film.getName() + " прошел проверки всех полей");
    }

    public Map<Integer, Film> getFilmMap() {
        return dataMap;
    }
}