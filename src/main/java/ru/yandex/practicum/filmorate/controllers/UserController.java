package ru.yandex.practicum.filmorate.controllers;

import com.google.gson.annotations.JsonAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.InputDataException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@JsonAdapter(User.class)
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends Controller<User> {

    @GetMapping
    public Collection<User> getAll() {
        return super.getAll();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        log.debug("create user");
        return super.create(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        log.debug("update user");
        return super.update(user);
    }
    public void validate(User user, boolean isCreate) {
        if (isCreate) {
            if (dataMap.containsKey(user.getId())) {
                throw new InputDataException("Невозможно добавить " + user.getName() + ", так как он был добавлен ранее");
            }
            log.debug("user " + user.getName() + " id" + user.getId() + " изменен");
        } else {
            if (!dataMap.containsKey(user.getId())) {
                throw new InputDataException("Невозможно обновить данные user, " + user.getName() + " не был добавлен ранее");
            }
            log.debug("user " + user.getName() + " id" + user.getId() + " изменен");
        }
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new InputDataException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        log.debug("user прошел проверку на Email");
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new InputDataException("Логин не может быть пустым или содержать пробелы");
        }
        log.debug("user прошел проверку на login");
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new InputDataException("Дата рождения не может быть в будущем");
        }
        log.debug("user прошел проверку на Birthday");
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("Полю name user-a присвоено значение login");
        }
    }
    public Map<Integer, User> getUserMap() {
        return dataMap;
    }
}