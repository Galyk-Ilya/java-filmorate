package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService extends AbstractService<Film> {

    private static final LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);
    Storage<User> userStorage;

    Logger log = LoggerFactory.getLogger(FilmService.class);

    @Autowired
    public FilmService(Storage<Film> filmStorage, Storage<User> userStorage) {
        entities = filmStorage;
        this.userStorage = userStorage;
    }

    @Override
    protected void validate(Film entity) {
        if (entity.getReleaseDate().isBefore(CINEMA_BIRTHDAY)) {
            throw new ValidationException("The release date must be - no earlier than December 28, 1895");
        }
        log.debug(entity.getName() + " passed the test for releaseDate");
    }


    public void likeCreate(int filmId, int userId) {
        if (userStorage.getAll().contains(userStorage.get(userId))) {
            entities.get(filmId).getLikesUsersId().add(userId);
            log.info("movie id:" + filmId + " was liked by person id:" + userId);
        }
    }

    public void likeDelete(int filmId, int userId) {
        if (userStorage.getAll().contains(userStorage.get(userId))) {
            entities.get(filmId).getLikesUsersId().remove(userId);
            log.info("movie id:" + filmId + " was deleted by person id:" + userId);
        }
    }

    public List<Film> popular(Optional<Integer> count) {
        if (count.isEmpty()) {
            count = Optional.of(10);
            log.info("set to 10 for count");
        }
        return entities.getAll().stream().
                sorted(new FilmPopular()).
                limit(count.get()).
                collect(Collectors.toList());
    }

static class FilmPopular implements Comparator<Film> {
    @Override
    public int compare(Film o1, Film o2) {
        return o2.getLikesUsersId().size() - o1.getLikesUsersId().size();
    }
}
}