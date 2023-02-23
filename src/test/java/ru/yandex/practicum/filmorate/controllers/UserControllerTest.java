package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.InputDataException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {
    List<User> users;
    UserController controller;

    @BeforeEach
    public void createControllerAndUsersForTests() {
        controller = new UserController();
        User userSuccess = new User(0, "xcaz@inbox.ru", "Ilya", "Xcaz", "1997-02-10");
        User userEmailTest1 = new User(0, "xcaz.inbox.ru", "Ilya", "Xcaz", "1997-02-10");
        User userEmailTest2 = new User(0, "", "Ilya", "Xcaz", "1997-02-10");
        User userLoginTest1 = new User(0, "xcaz@inbox.ru", "Ilya", "", "1997-02-10");
        User userLoginTest2 = new User(0, "xcaz@inbox.ru", "Ilya", " x c a z", "1997-02-10");
        User userBirthdayTest = new User(0, "xcaz@inbox.ru", "xcaz", "Ilya", "2025-02-10");
        User userNameTest = new User(0, "xcaz@inbox.ru", "", "Xcaz", "1997-02-10");
        users = List.of(userSuccess, userEmailTest1, userEmailTest2,
                userLoginTest1, userLoginTest2, userBirthdayTest, userNameTest);
    }

    @Test
    public void getAllTest() {
        assertEquals(0, controller.getAll().size());
        controller.getUserMap().put(1, users.get(0));
        assertEquals(1, controller.getAll().size());
    }

    @Test
    public void createTest() {
        assertEquals(0, controller.getUserMap().size());
        controller.create(users.get(0));
        assertEquals(1, controller.getUserMap().size());
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @",
                exceptionTestAddition(1).getMessage());
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @",
                exceptionTestAddition(2).getMessage());
        assertEquals("Логин не может быть пустым или содержать пробелы",
                exceptionTestAddition(3).getMessage());
        assertEquals("Логин не может быть пустым или содержать пробелы",
                exceptionTestAddition(4).getMessage());
        assertEquals("Дата рождения не может быть в будущем",
                exceptionTestAddition(5).getMessage());
        controller.create(users.get(6));
        assertEquals("Xcaz", controller.getUserMap().get(2).getName());
        assertEquals(2, controller.getUserMap().size());
        assertEquals("Невозможно добавить Xcaz, так как он был добавлен ранее",
                exceptionTestAddition(6).getMessage());
    }

    @Test
    public void updateTest() {
        assertEquals(0, controller.getUserMap().size());
        controller.getUserMap().put(1, users.get(0));
        users.forEach(film -> film.setId(1));
        controller.update(users.get(6));
        assertEquals("Xcaz", controller.getUserMap().get(1).getName());
        assertEquals(1, controller.getUserMap().size());
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @",
                exceptionTestUpdate(1).getMessage());
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @",
                exceptionTestUpdate(2).getMessage());
        assertEquals("Логин не может быть пустым или содержать пробелы",
                exceptionTestUpdate(3).getMessage());
        assertEquals("Логин не может быть пустым или содержать пробелы",
                exceptionTestUpdate(4).getMessage());
        assertEquals("Дата рождения не может быть в будущем", exceptionTestUpdate(5).getMessage());
        assertEquals(1, controller.getUserMap().size());
        users.get(6).setName("nameUpdateTest");
        assertEquals("nameUpdateTest", controller.update(users.get(6)).getName());
        users.get(6).setId(100);
        assertEquals("Невозможно обновить данные user, nameUpdateTest" + " не был добавлен ранее",
                exceptionTestUpdate(6).getMessage());
    }

    public InputDataException exceptionTestAddition(int listNumber) {
        return assertThrows(InputDataException.class, () -> controller.create(users.get(listNumber)));
    }

    public InputDataException exceptionTestUpdate(int listNumber) {
        return assertThrows(InputDataException.class, () -> controller.update(users.get(listNumber)));
    }
}
