package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.InputDataException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends Controller<User> {

    @Override
    public void validate(User user) {

        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new InputDataException("Email cannot be empty and must contain the @ symbol");
        }
        log.debug("user passed the test for Email");
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new InputDataException("Login cannot be empty or contain spaces");
        }
        log.debug("user passed the test for login");
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new InputDataException("Date of birth cannot be in the future");
        }
        log.debug("user passed the test for Birthday");
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("The name user field is set to login");
        }
    }
}