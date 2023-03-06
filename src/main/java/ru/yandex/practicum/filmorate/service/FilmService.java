package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService extends AbstractService<Film> {

    private static final LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        entities = filmStorage;
        this.userStorage = userStorage;
    }

    @Override
    protected void validate(Film entity) {
        if (entity.getReleaseDate().isBefore(CINEMA_BIRTHDAY)) {
            throw new ValidationException("The release date must be - no earlier than December 28, 1895");
        }
    }

    public void addLike(int filmId, int userId) {
        if (userStorage.get(userId) == null || entities.get(filmId) == null) {
            throw new NotFoundException("Not found id:" + filmId + " or " + userId);
        }
        entities.get(filmId).getLikedUserIds().add(userId);
        log.info("movie id:" + filmId + " was liked by person id:" + userId);
    }

    public void deleteLike(int filmId, int userId) {
        if (userStorage.get(userId) == null || entities.get(filmId) == null) {
            throw new NotFoundException("Not found id:" + filmId + " or " + userId);
        }
        entities.get(filmId).getLikedUserIds().remove(userId);
        log.info("movie id:" + filmId + " was deleted by person id:" + userId);
    }

    public List<Film> getMostPopularFilms(int count) {
        return entities.getAll().stream()
                .sorted((x, y) -> y.getLikedUserIds().size() - x.getLikedUserIds().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}