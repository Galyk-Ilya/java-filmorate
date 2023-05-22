package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Film createFilm(Film film);

    List<Film> findAllFilms();

    Film update(Film film);

    void deleteFilm(int filmId);

    Optional<Film> findById(int filmId);

    void addLike(int filmId, int userId);

    void deleteLike(int filmId, int userId);

    List<Film> getPopularFilms(int count);

    Optional<User> findUserById(int userId);
}