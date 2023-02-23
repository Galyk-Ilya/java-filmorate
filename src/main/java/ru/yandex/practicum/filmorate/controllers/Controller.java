package ru.yandex.practicum.filmorate.controllers;

import ru.yandex.practicum.filmorate.model.Model;
import java.util.*;

public abstract class Controller<T extends Model> {

    protected final HashMap<Integer, T> dataMap = new HashMap<>();
    Integer counter = 0;

    public T create(T data) {
        validate(data, true);
        data.setId(++counter);
        dataMap.put(data.getId(), data);
        return data;
    }

    public T update(T data) {
        validate(data, false);
        dataMap.put(data.getId(), data);
        return data;
    }

    public Collection<T> getAll() {
        return dataMap.values();
    }

    public abstract void validate(T data, boolean isCreate);


}
