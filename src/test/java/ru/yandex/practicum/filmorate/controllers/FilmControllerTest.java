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
//    private FilmController controller;
//    private List<Film> films;
//    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//    private final LocalDate localDate = LocalDate.parse("2002-02-07", formatter);
//
//    @BeforeEach
//    public void createForTests() {
//        String description200 = "1".repeat(200);
//        String description201 = "1".repeat(201);
//        controller = new FilmController();
//        Film filmSuccess = new Film(1, "наследство", "Властелин колец 1",
//                localDate, 178);
//        Film filmNameTest1 = new Film(2, "наследство", "",
//                localDate, 178);
//        Film filmDescriptionTest1 = new Film(2, description200, "Властелин колец",
//                localDate, 178);
//        Film filmDescriptionTest2 = new Film(3, "", "Властелин колец",
//                localDate, 178);
//        Film filmDescriptionTest3 = new Film(4, description201, "Властелин колец",
//                localDate, 178);
//        Film filmReleaseDateTest1 = new Film(4, "", "Властелин колец",
//                LocalDate.parse("1895-12-27", formatter), 178);
//        Film filmDurationTest1 = new Film(4, "", "Властелин колец",
//                localDate, -178);
//
//        films = List.of(filmSuccess, filmNameTest1, filmDescriptionTest1,
//                filmDescriptionTest2, filmDescriptionTest3, filmReleaseDateTest1, filmDurationTest1);
//    }
//
//    @Test
//    public void getAllTest() {
//        assertEquals(0, controller.getAll().size());
//        controller.dataMap.put(1, films.get(0));
//        assertEquals(1, controller.getAll().size());
//    }
//
//    @Test
//    public void createTest() {
//        assertEquals(0, controller.dataMap.size());
//        controller.create(films.get(0));
//        assertEquals(1, controller.dataMap.size());
//        assertEquals("Movie title cannot be empty",
//                exceptionTestAddition(1).getMessage());
//
//        assertEquals(films.get(2), controller.create(films.get(2)));
//        assertEquals(films.get(3), controller.create(films.get(3)));
//        assertEquals("The maximum description length is 200 characters",
//                exceptionTestAddition(4).getMessage());
//
//        assertEquals("The release date must be - no earlier than December 28, 1895",
//                exceptionTestAddition(5).getMessage());
//        assertEquals("Movie duration must be positive",
//                exceptionTestAddition(6).getMessage());
//        assertEquals(3, controller.dataMap.size());
//    }
//
//    @Test
//    public void updateTest() {
//        assertEquals(0, controller.dataMap.size());
//        films.forEach(film -> film.setId(1));
//        controller.dataMap.put(1, films.get(0));
//        assertEquals("Movie title cannot be empty", exceptionTestUpdate(1).getMessage());
//        assertEquals(films.get(2), controller.update(films.get(2)));
//        assertEquals(films.get(3), controller.update(films.get(3)));
//        assertEquals("The maximum description length is 200 characters",
//                exceptionTestUpdate(4).getMessage());
//
//        assertEquals("The release date must be - no earlier than December 28, 1895",
//                exceptionTestUpdate(5).getMessage());
//        assertEquals("Movie duration must be positive",
//                exceptionTestUpdate(6).getMessage());
//        assertEquals(1, controller.dataMap.size());
//        films.get(0).setName("nameUpdateTest");
//        assertEquals("nameUpdateTest", controller.update(films.get(0)).getName());
//        films.get(6).setId(100);
//        assertEquals("Unable to update id100 has not been added before",
//                exceptionTestUpdate(6).getMessage());
//    }
//
//    private InputDataException exceptionTestAddition(int listNumber) {
//        return assertThrows(InputDataException.class, () -> controller.create(films.get(listNumber)));
//    }
//
//    private InputDataException exceptionTestUpdate(int listNumber) {
//        return assertThrows(InputDataException.class, () -> controller.update(films.get(listNumber)));
//    }
}
