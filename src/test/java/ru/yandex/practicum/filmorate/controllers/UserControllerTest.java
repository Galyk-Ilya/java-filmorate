package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.exceptions.InputDataException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {
    private UserController controller;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final LocalDate localDate = LocalDate.parse("1997-02-10", formatter);

    @BeforeEach
    public void createController() {
        controller = new UserController();
    }

    @Test
    public void getAllTest() {
        List<User> users = createUsers();
        assertEquals(0, controller.getAll().size());
        controller.entities.put(1, users.get(0));
        assertEquals(1, controller.getAll().size());
    }

    @Test
    public void createTest() {
        List<User> users = createUsers();
        assertEquals(0, controller.entities.size());
        controller.create(users.get(0));
        assertEquals(1, controller.entities.size());
        controller.create(users.get(1));
        assertEquals("Xcaz", controller.entities.get(2).getName());
        assertEquals(2, controller.entities.size());
    }

    @Test
    public void updateTest() {
        List<User> users = createUsers();
        assertEquals(0, controller.entities.size());
        controller.entities.put(1, users.get(0));
        users.forEach(film -> film.setId(1));
        controller.update(users.get(1));
        assertEquals("Xcaz", controller.entities.get(1).getName());
        assertEquals(1, controller.entities.size());
        assertEquals(1, controller.entities.size());
        users.get(1).setName("nameUpdateTest");
        assertEquals("nameUpdateTest", controller.update(users.get(1)).getName());
        users.get(1).setId(100);
        assertEquals("Unable to update id100 has not been added before",
                assertThrows(InputDataException.class, () -> controller.update(users.get(1))).getMessage());
    }

    public List<User> createUsers() {
        List<User> users;
        User userSuccess = new User("xcaz@inbox.ru", "Ilya", "Xcaz", localDate);
        User userNameTest = new User("xcaz@inbox.ru", "", "Xcaz", localDate);
        users = List.of(userSuccess, userNameTest);
        return users;
    }
}