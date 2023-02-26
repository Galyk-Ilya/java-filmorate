package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.exceptions.InputDataException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {
    private FilmController controller;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final LocalDate localDate = LocalDate.parse("2002-02-07", formatter);

    @BeforeEach
    public void createForTests() {
        controller = new FilmController();
    }

    @Test
    public void getAllTest() {
        List<Film> films = createFilmList();
        assertEquals(0, controller.getAll().size());
        controller.entities.put(1, films.get(0));
        assertEquals(1, controller.getAll().size());
    }

    @Test
    public void createTest() {
        List<Film> films = createFilmList();
        assertEquals(0, controller.entities.size());
        controller.create(films.get(0));
        assertEquals(1, controller.entities.size());

        assertEquals(films.get(2), controller.create(films.get(2)));
        assertEquals(films.get(3), controller.create(films.get(3)));
        assertEquals("The release date must be - no earlier than December 28, 1895",
                assertThrows(InputDataException.class, () -> controller.create(films.get(5))).getMessage());
        assertEquals(3, controller.entities.size());
    }

    @Test
    public void updateTest() {
        List<Film> films = createFilmList();
        assertEquals(0, controller.entities.size());
        films.forEach(film -> film.setId(1));
        controller.entities.put(1, films.get(0));
        assertEquals(films.get(2), controller.update(films.get(2)));
        assertEquals(films.get(3), controller.update(films.get(3)));
        assertEquals("The release date must be - no earlier than December 28, 1895",
                exceptionTestUpdate(films, 5).getMessage());
        assertEquals(1, controller.entities.size());
        films.get(0).setName("nameUpdateTest");
        assertEquals("nameUpdateTest", controller.update(films.get(0)).getName());
        films.get(6).setId(100);
        assertEquals("Unable to update id100 has not been added before",
                exceptionTestUpdate(films, 6).getMessage());
    }

    private InputDataException exceptionTestUpdate(List<Film> films, int listNumber) {
        return assertThrows(InputDataException.class, () -> controller.update(films.get(listNumber)));
    }

    public List<Film> createFilmList() {
        List<Film> films;
        String description200 = "1".repeat(200);
        String description201 = "1".repeat(201);
        Film filmSuccess = new Film("наследство", "Властелин колец 1",
                localDate, 178);
        filmSuccess.setId(1);
        Film filmNameTest1 = new Film("наследство", "",
                localDate, 178);
        filmNameTest1.setId(2);
        Film filmDescriptionTest1 = new Film(description200, "Властелин колец",
                localDate, 178);
        filmDescriptionTest1.setId(2);
        Film filmDescriptionTest2 = new Film("", "Властелин колец",
                localDate, 178);
        filmDescriptionTest2.setId(3);
        Film filmDescriptionTest3 = new Film(description201, "Властелин колец",
                localDate, 178);
        filmDescriptionTest3.setId(4);
        Film filmReleaseDateTest1 = new Film("", "Властелин колец",
                LocalDate.parse("1895-12-27", formatter), 178);
        filmReleaseDateTest1.setId(4);
        Film filmDurationTest1 = new Film("", "Властелин колец",
                localDate, -178);
        filmDurationTest1.setId(4);
        films = List.of(filmSuccess, filmNameTest1, filmDescriptionTest1,
                filmDescriptionTest2, filmDescriptionTest3, filmReleaseDateTest1, filmDurationTest1);
        return films;
    }
}