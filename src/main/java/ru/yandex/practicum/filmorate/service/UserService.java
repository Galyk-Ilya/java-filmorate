package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService extends AbstractService<User> {

    @Autowired
    public UserService(Storage<User> userStorage) {
        entities = userStorage;
    }

    @Override
    protected void validate(User user) {
        preSave(user);
    }

    public void preSave(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("The name user field is set to login");
        }
    }

    public void addFriend(int id, int friendId) {
        if (entities.getAll().contains(entities.get(id)) && entities.getAll().contains(entities.get(friendId))) {
            entities.get(id).getFriendId().add(friendId);
            entities.get(friendId).getFriendId().add(id);
            log.info(id + " and " + friendId + " added to friends");
        }
    }

    public void deleteFriend(int id, int friendId) {
        if (entities.getAll().contains(entities.get(id)) && entities.getAll().contains(entities.get(friendId))) {
            entities.get(id).getFriendId().remove(friendId);
            entities.get(friendId).getFriendId().remove(id);
            log.info(id + " and " + friendId + " removed from friends");

        }
    }

    public List<User> getFriends(int id) {
        return entities.get(id).getFriendId().stream()
                .map(integer -> entities.get(integer))
                .collect(Collectors.toList());
    }

    public List<User> getMutualFriends(int id, int otherId) {
        return entities.get(id).getFriendId().stream()
                .filter(integer -> entities.get(otherId).getFriendId().contains(integer))
                .map(integer -> entities.get(integer))
                .collect(Collectors.toList());
    }
}