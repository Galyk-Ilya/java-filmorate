package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Film createFilm(Film film);

    List<Film> findAllFilms();

    Film update(Film film);

    //    public Film findById(int filmId) {
    //        final String sqlQuery = "SELECT * FROM films WHERE id = ?";
    //        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sqlQuery, filmId);
    //
    //        if (filmRows.next()) {
    //            log.warn("Movie with id {} found.", filmId);
    //            return jdbcTemplate.queryForObject(sqlQuery, mapperFilm, filmId);
    //        } else {
    //            log.warn("Movie with id {} was not found.", filmId);
    //            throw new NotFoundException("Movie not found");
    //        }
    //    }

    void deleteFilm(int filmId);

    Optional<Film> findById(int filmId);

    void addLike(int filmId, int userId);

    void deleteLike(int filmId, int userId);

    List<Film> getPopularFilms(int count);

    Optional<User> findUserById(int userId);
}