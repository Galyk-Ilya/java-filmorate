package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;

public abstract class AbstractService<T extends Model> {
    Storage<T> entities;

    public T create(T entity) {
        entities.create(entity);
        return entity;
    }

    public T update(T entity) {
        entities.update(entity);
        return entity;
    }

    public List<T> getAll() {
        return entities.getAll();
    }

    public T getEntity(long id) {
        return entities.get(id);
    }

    public void deleteEntity(int id) {
        entities.delete(id);
    }

    protected abstract void validate(T entity);

}