package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public List<User> getAll() {
        return userStorage.findAllUsers();
    }

    public User create(User user) {
        preSave(user);
        return userStorage.createUser(user);
    }

    public User getUserById(Integer userId) {
        return userStorage.get(userId);
    }

    public User update(User user) {
        preSave(user);
        return userStorage.update(user);
    }

    public User deleteUser(int id) {
        return userStorage.deleteUser(id);
    }

    public void addFriend(int userId, int friendId) {
        userStorage.addFriend(userId, friendId);
        log.info(userId + " and " + friendId + " added to friends");
    }

    public void deleteFriend(int userId, int friendId) {
        userStorage.deleteFriend(userId, friendId);
        log.info(userId + " and " + friendId + " removed from friends");
    }

    public List<User> getUserFriends(int userId) {
        return userStorage.getUserFriends(userId);
    }

    public List<User> getMutualFriends(int userId, int otherUserId) {
        return userStorage.getMutualFriends(userId, otherUserId);
    }

    private void preSave(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("The name user field is set to login");
        }
    }
}