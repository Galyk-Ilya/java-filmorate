package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.InputDataException;
import ru.yandex.practicum.filmorate.model.Model;

import java.util.HashMap;
import java.util.List;

@Slf4j
public class AbstractStorage <T extends Model> implements Storage<T> {
    protected final HashMap<Integer, T> entities = new HashMap<>();
    private int counter = 0;

    public void create(T entity) {
        validate(entity);
        entity.setId(++counter);
        entities.put(entity.getId(), entity);
        log.debug("create entity, id " + entity.getId());
    }

    public void update(T entity) {
        if (!entities.containsKey(entity.getId())) {
            throw new InputDataException("Unable to update id" + entity.getId() + " has not been added before");
        }
        log.debug("id" + entity.getId() + " updated");
        validate(entity);
        entities.put(entity.getId(), entity);
        log.debug("update entity, id " + entity.getId());
    }

    public List<T> getAll() {
        log.debug("give all entities");
        return (List<T>) entities.values();
    }

    @Override
    public T get(long id) {
        return null;
    }

    @Override
    public void delete(int id) {
        entities.remove(id);
    }

    protected void validate(T entity){}

    public void preSave(T entity) {}
}