package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class AbstractStorage<T extends Model> implements Storage<T> {
    protected final HashMap<Integer, T> entities = new HashMap<>();
    private int counter = 0;

    public void create(T entity) {
        entity.setId(++counter);
        entities.put(entity.getId(), entity);
        log.debug("create entity, id " + entity.getId());
    }

    public void update(T entity) {
        entities.put(entity.getId(), entity);
        log.debug("update entity, id " + entity.getId());
    }

    public List<T> getAll() {
        log.debug("give all entities");
        return new ArrayList<>(entities.values());
    }

    @Override
    public T get(int id) {
        if (!entities.containsKey(id)) {
            throw new NotFoundException("Not found id:" + id);
        }
        return entities.get(id);
    }

    @Override
    public void delete(int id) {
        entities.remove(id);
    }
}