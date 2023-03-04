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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
    public Film create(@Valid @RequestBody Film entity) {
        return service.create(entity);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film entity) {
        return service.update(entity);
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

    @GetMapping(value = {"/popular", "/popular?count={count}"})
    public List<Film> popular(@RequestParam Optional<Integer> count) {
        return service.popular(count);
    }
}