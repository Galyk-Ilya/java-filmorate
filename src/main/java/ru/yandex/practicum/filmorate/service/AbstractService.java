package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;

public abstract class AbstractService<T extends Model> {
    protected Storage<T> entities;

    private int counter = 0;

    protected static Logger log = LoggerFactory.getLogger(AbstractService.class);

    public T create(T entity) {
        validate(entity);
        entity.setId(++counter);
        entities.create(entity);
        return entity;
    }

    public T update(T entity) {
        if (entities.get(entity.getId()) == null) {
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

    protected void validate(T entity) {}
}