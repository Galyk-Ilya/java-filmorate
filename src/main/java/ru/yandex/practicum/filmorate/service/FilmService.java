package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService extends AbstractService<Film> {
    @Override
    protected void validate(Film entity) {

    }

    public void likeCreate(int filmId, int userId) {
        entities.get(filmId).getLikesUsersId().add(userId);
    }

    public void likeDelete(int filmId, int userId) {
        entities.get(filmId).getLikesUsersId().remove(userId);
    }

    public List<Film> popular(int count) {
        return entities.getAll().stream().
                map(film -> film.getLikesUsersId().size()).
                sorted().
                limit(count).
                map(integer -> entities.getAll().get(integer)).
                collect(Collectors.toList());
    }
}