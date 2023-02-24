package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.yandex.practicum.filmorate.exceptions.InputDataException;
import ru.yandex.practicum.filmorate.model.Model;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
public abstract class Controller<T extends Model> {

    protected final HashMap<Integer, T> dataMap = new HashMap<>();
    private int counter = 0;

    @PostMapping
    public T create(T data) {
        validate(data);
        data.setId(++counter);
        dataMap.put(data.getId(), data);
        return data;
    }

    @PutMapping
    public T update(T data) {
        if (!dataMap.containsKey(data.getId())) {
            throw new InputDataException("Unable to update " + data.getName() + " has not been added before");
        }
        log.debug(data.getName() + ", id" + data.getId() + " updated");
        validate(data);
        dataMap.put(data.getId(), data);
        return data;
    }

    @GetMapping
    public Collection<T> getAll() {
        return dataMap.values();
    }

    public abstract void validate(T data);
}