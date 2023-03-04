package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    final FilmService service;

    @Autowired
    public FilmController(FilmService service) {
        this.service = service;
    }

    @PostMapping
    public void create(@Valid @RequestBody Film entity) {
        service.create(entity);
    }

    @PutMapping
    public void update(@Valid @RequestBody Film entity) {
        service.update(entity);
    }

    @GetMapping
    public List<Film> getFilms() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.deleteEntity(id);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable int id) {
        return service.getEntity(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeCreate(@PathVariable int id, @PathVariable int userId) {
        service.likeCreate(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void likeDelete(@PathVariable int id, @PathVariable int userId) {
        service.likeDelete(id, userId);
    }

    @GetMapping("/popular?count={count}")
    public List<Film> popular(@PathVariable(required = false) int count) {
        if (count == 0) {
            count = 10;
        }
        return service.popular(count);
    }
//    PUT /films/{id}/like/{userId} — пользователь ставит лайк фильму.
//    DELETE /films/{id}/like/{userId} — пользователь удаляет лайк.
//            GET /films/popular?count={count} — возвращает список из первых count
//    фильмов по количеству лайков. Если значение параметра count не задано, верните первые 10.
}