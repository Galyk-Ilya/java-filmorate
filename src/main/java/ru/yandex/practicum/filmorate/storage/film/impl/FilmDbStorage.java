package ru.yandex.practicum.filmorate.storage.film.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.MapperFilm;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.impl.GenreDbStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MapperFilm mapperFilm;
    private final GenreDbStorage genreDbStorage;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, MapperFilm mapperFilm, GenreDbStorage genreDbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapperFilm = mapperFilm;
        this.genreDbStorage = genreDbStorage;
    }

    @Override
    public List<Film> findAllFilms() {
        final String sqlQuery = "select * from films";

        return jdbcTemplate.query(sqlQuery, mapperFilm);
    }

    @Override
    public Film createFilm(Film film) {

        if (film.getGenres() != null) {
            for (Genre gch : film.getGenres()) {
                if (gch.getId() == null || gch.getName() == null) {
                    throw new NotFoundException("No data");
                }
            }
        }

        final String sqlQuery = "INSERT INTO films (name, description, release_date, duration, mpa_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        KeyHolder generatedId = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setLong(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());

            return stmt;
        }, generatedId);

        film.setId(Objects.requireNonNull(generatedId.getKey()).intValue());

        if (film.getGenres() != null) {
            final String genresSqlQuery = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
            for (Genre g : film.getGenres()) {
                if (g.getId() != null || g.getName() != null) {
                    jdbcTemplate.update(genresSqlQuery, film.getId(), g.getId());
                }
            }
        }

        return film;
    }

    @Override
    public Film update(Film film) {
        getFilmById(film.getId());

        final String sqlQuery = "UPDATE films SET name = ?, description = ?, release_date = ?, " +
                "duration = ?, mpa_id = ?" +
                "WHERE id = ?";

        if (film.getGenres() != null) {
            final String deleteGenresQuery = "DELETE FROM film_genre WHERE film_id = ?";
            final String updateGenresQuery = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";

            jdbcTemplate.update(deleteGenresQuery, film.getId());
            for (Genre g : film.getGenres()) {
                String checkDuplicate = "SELECT * FROM film_genre WHERE film_id = ? AND genre_id = ?";
                SqlRowSet checkRows = jdbcTemplate.queryForRowSet(checkDuplicate, film.getId(), g.getId());
                if (!checkRows.next()) {
                    jdbcTemplate.update(updateGenresQuery, film.getId(), g.getId());
                }
            }
        }
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        film.setGenres(genreDbStorage.getByFilmId(film.getId()));
        return film;
    }

    @Override
    public Film getFilmById(int filmId) {
        final String sqlQuery = "SELECT * FROM films WHERE id = ?";
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sqlQuery, filmId);

        if (filmRows.next()) {
            log.warn("Movie with id {} found.", filmId);
            return jdbcTemplate.queryForObject(sqlQuery, mapperFilm, filmId);
        } else {
            log.warn("Movie with id {} was not found.", filmId);
            throw new NotFoundException("Movie not found");
        }
    }

    @Override
    public Film deleteFilm(int id) {
        Film film = getFilmById(id);
        final String genresSqlQuery = "DELETE FROM film_genre WHERE film_id = ?";
        String mpaSqlQuery = "DELETE FROM mpa WHERE id = ?";

        jdbcTemplate.update(genresSqlQuery, id);
        jdbcTemplate.update(mpaSqlQuery, id);
        final String sqlQuery = "DELETE FROM films WHERE id = ?";

        jdbcTemplate.update(sqlQuery, id);
        return film;
    }

    @Override
    public void addLike(int filmId, int userId) {
        validate(filmId, userId);
        final String sqlQuery = "INSERT INTO films_likes (film_id, user_id) VALUES (?, ?)";
        log.info("User {} liked the movie {}", userId, filmId);
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        validate(filmId, userId);
        final String sqlQuery = "DELETE FROM films_likes " +
                "WHERE film_id = ? AND user_id = ?";

        jdbcTemplate.update(sqlQuery, filmId, userId);
        log.info("User {} deleted the like for the movie {}", userId, filmId);
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        String sqlQuery = "select f.* from films f " +
                "left join films_likes fl on f.id = fl.film_id " +
                "group by  f.id " +
                "order by count(fl.user_id) desc " +
                "limit ?";

        return jdbcTemplate.query(sqlQuery, mapperFilm, count);
    }

    private void validate(int filmId, int userId) {
        final String checkFilmQuery = "SELECT * FROM films WHERE id = ?";
        final String checkUserQuery = "SELECT * FROM users WHERE id = ?";

        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(checkFilmQuery, filmId);
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(checkUserQuery, userId);

        if (!filmRows.next() || !userRows.next()) {
            log.warn("Movie {} and/or user {} not found.", filmId, userId);
            throw new NotFoundException("Movie or user not found");
        }
    }
}