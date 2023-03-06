package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
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
    protected void preSave(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("The name user field is set to login");
        }
    }

    public void addFriend(int id, int friendId) {
        if (entities.get(id) == null || entities.get(friendId) == null) {
            throw new NotFoundException("Not found id:" + id + " or " + friendId);
        }
        entities.get(id).getFriendsId().add(friendId);
        entities.get(friendId).getFriendsId().add(id);
        log.info(id + " and " + friendId + " added to friends");
    }

    public void deleteFriend(int id, int friendId) {
        if (entities.get(id) == null || entities.get(friendId) == null) {
            throw new NotFoundException("Not found id:" + id + " or " + friendId);
        }
        entities.get(id).getFriendsId().remove(friendId);
        entities.get(friendId).getFriendsId().remove(id);
        log.info(id + " and " + friendId + " removed from friends");
    }

    public List<User> getFriends(int id) {
        return entities.get(id).getFriendsId().stream()
                .map(integer -> entities.get(integer))
                .collect(Collectors.toList());
    }

    public List<User> getMutualFriends(int id, int otherId) {
        return entities.get(id).getFriendsId().stream()
                .filter(integer -> entities.get(otherId).getFriendsId().contains(integer))
                .map(integer -> entities.get(integer))
                .collect(Collectors.toList());
    }
}