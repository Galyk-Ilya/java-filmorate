package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exceptions.InputDataException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {
    FilmController controller;
    List<Film> films;

    @BeforeEach
    public void createForTests() {
        String description200 = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111";
        String description201 = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111";
        controller = new FilmController();
        Film filmSuccess = new Film(1, "наследство", "Властелин колец 1",
                "2002-02-07", 178);
        Film filmNameTest1 = new Film(2, "наследство", "",
                "2002-02-07", 178);
        Film filmDescriptionTest1 = new Film(2, description200, "Властелин колец",
                "2002-02-07", 178);
        Film filmDescriptionTest2 = new Film(3, "", "Властелин колец",
                "2002-02-07", 178);
        Film filmDescriptionTest3 = new Film(4, description201, "Властелин колец",
                "2002-02-07", 178);
        Film filmReleaseDateTest1 = new Film(4, "", "Властелин колец",
                "1895-12-27", 178);
        Film filmDurationTest1 = new Film(4, "", "Властелин колец",
                "2002-02-07", -178);

        films = List.of(filmSuccess, filmNameTest1, filmDescriptionTest1,
                filmDescriptionTest2, filmDescriptionTest3, filmReleaseDateTest1, filmDurationTest1);
    }

    @Test
    public void getAllTest() {
        assertEquals(0, controller.getAll().size());
        controller.getFilmMap().put(1, films.get(0));
        assertEquals(1, controller.getAll().size());
    }

    @Test
    public void createTest() {
        assertEquals(0, controller.getFilmMap().size());
        controller.create(films.get(0));
        assertEquals(1, controller.getFilmMap().size());
        assertEquals("Название фильма не может быть пустым",
                exceptionTestAddition(1).getMessage());

        assertEquals(films.get(2), controller.create(films.get(2)));
        assertEquals(films.get(3), controller.create(films.get(3)));
        assertEquals("Максимальная длина описания — 200 символов",
                exceptionTestAddition(4).getMessage());

        assertEquals("Дата релиза должна быть — не раньше 28 декабря 1895 года",
                exceptionTestAddition(5).getMessage());
        assertEquals("Продолжительность фильма должна быть положительной",
                exceptionTestAddition(6).getMessage());
        assertEquals(3, controller.getFilmMap().size());
        assertEquals("Невозможно добавить Властелин колец 1, так как он был добавлен ранее",
                exceptionTestAddition(0).getMessage());
    }

    @Test
    public void updateTest() {
        assertEquals(0, controller.getFilmMap().size());
        films.forEach(film -> film.setId(1));
        controller.getFilmMap().put(1, films.get(0));
        assertEquals("Название фильма не может быть пустым", exceptionTestUpdate(1).getMessage());
        assertEquals(films.get(2), controller.update(films.get(2)));
        assertEquals(films.get(3), controller.update(films.get(3)));
        assertEquals("Максимальная длина описания — 200 символов",
                exceptionTestUpdate(4).getMessage());

        assertEquals("Дата релиза должна быть — не раньше 28 декабря 1895 года",
                exceptionTestUpdate(5).getMessage());
        assertEquals("Продолжительность фильма должна быть положительной",
                exceptionTestUpdate(6).getMessage());
        assertEquals(1, controller.getFilmMap().size());
        films.get(0).setName("nameUpdateTest");
        assertEquals("nameUpdateTest", controller.update(films.get(0)).getName());
        films.get(6).setId(100);
        assertEquals("Невозможно обновить данные фильма, Властелин колец не был добавлен ранее",
                exceptionTestUpdate(6).getMessage());
    }

    public InputDataException exceptionTestAddition(int listNumber) {
        return assertThrows(InputDataException.class, () -> controller.create(films.get(listNumber)));
    }

    public InputDataException exceptionTestUpdate(int listNumber) {
        return assertThrows(InputDataException.class, () -> controller.update(films.get(listNumber)));
    }
}
