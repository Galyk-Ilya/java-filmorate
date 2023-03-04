package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractService<T extends Model> {
    Storage<T> entities;

    private int counter = 0;

    Logger log = LoggerFactory.getLogger(AbstractService.class);

    public T create(T entity) {
        validate(entity);
        entity.setId(++counter);
        entities.create(entity);
        return entity;
    }

    public T update(T entity) {
        if (!entities.getAll().stream().map(Model::getId).collect(Collectors.toList()).contains(entity.getId())) {
            throw new NotFoundException("Unable to update id" + entity.getId() + " has not been added before");
        }
        log.debug("id" + entity.getId() + " updated");
        validate(entity);
        entities.update(entity);
        return entity;
    }

    public List<T> getAll() {
        return entities.getAll();
    }

    public T getEntity(int id) {
        return entities.get(id);
    }

    public void deleteEntity(int id) {
        entities.delete(id);
    }

    protected abstract void validate(T entity);
}