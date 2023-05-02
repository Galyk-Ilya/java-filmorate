package ru.yandex.practicum.filmorate.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.impl.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.storage.user.impl.UserDbStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DbTest {
    private final UserDbStorage userStorage;
    private final FilmDbStorage filmStorage;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;

    @BeforeEach
    public void create() {
        User user = new User(1, "email", "login", "name", LocalDate.of(1997, 2, 10));
        userStorage.createUser(user);
        Mpa mpa = new Mpa(5, "NC-17");
        Genre genre = new Genre(1, "Комедия");
        Film film = new Film(1, "name", "description", LocalDate.of(1955, 1, 28), 120, List.of(genre), mpa);
        filmStorage.createFilm(film);
    }

    @Test
    public void testFindUserById() {
        Optional<User> userOptional = Optional.ofNullable(userStorage.get(1));
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void testFindFilmById() {
        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getFilmById(1));
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void testFindFilms() {
        Optional<Collection<Film>> filmsOptional = Optional.ofNullable(filmStorage.findAllFilms());
        assertThat(filmsOptional)
                .isPresent()
                .hasValueSatisfying(films ->
                        assertThat(films).hasSizeGreaterThan(0)
                );
    }

    @Test
    public void testFindUsers() {
        Optional<List<User>> usersOptional = Optional.ofNullable(userStorage.findAllUsers());
        assertThat(usersOptional)
                .isPresent()
                .hasValueSatisfying(users ->
                        assertThat(users).hasSizeGreaterThan(0)
                );
    }

    @Test
    public void testFindMpa() {
        Optional<Collection<Mpa>> mpaOptional = Optional.ofNullable(mpaStorage.findAll());
        assertThat(mpaOptional)
                .isPresent()
                .hasValueSatisfying(mpa ->
                        assertThat(mpa).hasSizeLessThanOrEqualTo(5)
                );
    }

    @Test
    public void testFindGenres() {
        Optional<Collection<Genre>> genreOptional = Optional.ofNullable(genreStorage.findAll());
        assertThat(genreOptional)
                .isPresent()
                .hasValueSatisfying(genres ->
                        assertThat(genres).hasSizeLessThanOrEqualTo(6)
                );
    }
}