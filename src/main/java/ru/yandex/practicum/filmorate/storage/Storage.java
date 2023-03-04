package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Model;

import java.util.List;

public interface Storage<T extends Model> {
    void create(T entity);

    void update(T entity);

    List<T> getAll();

    T get(long id);

    void delete(int id);
}
