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
//    private List<User> users;
//    private UserController controller;
//    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//    private final LocalDate localDate = LocalDate.parse("1997-02-10", formatter);
//
//    @BeforeEach
//    public void createControllerAndUsersForTests() {
//        controller = new UserController();
//
//
//        User userSuccess = new User(0, "xcaz@inbox.ru", "Ilya", "Xcaz", localDate);
//        User userEmailTest1 = new User(0, "xcaz.inbox.ru", "Ilya", "Xcaz", localDate);
//        User userEmailTest2 = new User(0, "", "Ilya", "Xcaz", localDate);
//        User userLoginTest1 = new User(0, "xcaz@inbox.ru", "Ilya", "", localDate);
//        User userLoginTest2 = new User(0, "xcaz@inbox.ru", "Ilya", " x c a z", localDate);
//        User userBirthdayTest = new User(0, "xcaz@inbox.ru", "xcaz", "Ilya",
//                LocalDate.parse("2025-02-10", formatter));
//        User userNameTest = new User(0, "xcaz@inbox.ru", "", "Xcaz", localDate);
//        users = List.of(userSuccess, userEmailTest1, userEmailTest2,
//                userLoginTest1, userLoginTest2, userBirthdayTest, userNameTest);
//    }
//
//    @Test
//    public void getAllTest() {
//        assertEquals(0, controller.getAll().size());
//        controller.dataMap.put(1, users.get(0));
//        assertEquals(1, controller.getAll().size());
//    }
//
//    @Test
//    public void createTest() {
//        assertEquals(0, controller.dataMap.size());
//        controller.create(users.get(0));
//        assertEquals(1, controller.dataMap.size());
//        assertEquals("Email cannot be empty and must contain the @ symbol",
//                exceptionTestAddition(1).getMessage());
//        assertEquals("Email cannot be empty and must contain the @ symbol",
//                exceptionTestAddition(2).getMessage());
//        assertEquals("Login cannot be empty or contain spaces",
//                exceptionTestAddition(3).getMessage());
//        assertEquals("Login cannot be empty or contain spaces",
//                exceptionTestAddition(4).getMessage());
//        assertEquals("Date of birth cannot be in the future",
//                exceptionTestAddition(5).getMessage());
//        controller.create(users.get(6));
//        assertEquals("Xcaz", controller.dataMap.get(2).getName());
//        assertEquals(2, controller.dataMap.size());
//    }
//
//    @Test
//    public void updateTest() {
//        assertEquals(0, controller.dataMap.size());
//        controller.dataMap.put(1, users.get(0));
//        users.forEach(film -> film.setId(1));
//        controller.update(users.get(6));
//        assertEquals("Xcaz", controller.dataMap.get(1).getName());
//        assertEquals(1, controller.dataMap.size());
//        assertEquals("Email cannot be empty and must contain the @ symbol",
//                exceptionTestUpdate(1).getMessage());
//        assertEquals("Email cannot be empty and must contain the @ symbol",
//                exceptionTestUpdate(2).getMessage());
//        assertEquals("Login cannot be empty or contain spaces",
//                exceptionTestUpdate(3).getMessage());
//        assertEquals("Login cannot be empty or contain spaces",
//                exceptionTestUpdate(4).getMessage());
//        assertEquals("Date of birth cannot be in the future", exceptionTestUpdate(5).getMessage());
//        assertEquals(1, controller.dataMap.size());
//        users.get(6).setName("nameUpdateTest");
//        assertEquals("nameUpdateTest", controller.update(users.get(6)).getName());
//        users.get(6).setId(100);
//        assertEquals("Unable to update id100 has not been added before",
//                exceptionTestUpdate(6).getMessage());
//    }
//
//    private InputDataException exceptionTestAddition(int listNumber) {
//        return assertThrows(InputDataException.class, () -> controller.create(users.get(listNumber)));
//    }
//
//    private InputDataException exceptionTestUpdate(int listNumber) {
//        return assertThrows(InputDataException.class, () -> controller.update(users.get(listNumber)));
//    }
}