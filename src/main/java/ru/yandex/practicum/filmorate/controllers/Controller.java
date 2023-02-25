package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.InputDataException;
import ru.yandex.practicum.filmorate.model.Model;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
public abstract class Controller<T extends Model> {

    protected final HashMap<Integer, T> entities = new HashMap<>();
    private int counter = 0;

    @PostMapping
    public T create(@RequestBody T entity) {
        validate(entity);
        entity.setId(++counter);
        entities.put(entity.getId(), entity);
        log.debug("create entity, id " + entity.getId());
        return entity;
    }

    @PutMapping
    public T update(@RequestBody T entity) {
        if (!entities.containsKey(entity.getId())) {
            throw new InputDataException("Unable to update id" + entity.getId() + " has not been added before");
        }
        log.debug("id" + entity.getId() + " updated");
        validate(entity);
        entities.put(entity.getId(), entity);
        log.debug("update entity, id " + entity.getId());
        return entity;
    }

    @GetMapping
    public Collection<T> getAll() {
        log.debug("give all entities");
        return entities.values();
    }

    public void validate(T entity){}
}