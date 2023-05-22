package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

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
        User user = userStorage.get(userId).orElseThrow(() -> new NotFoundException("User is not found"));
        log.debug("User with id {} found.", userId);
        return user;
    }

    public User update(User user) {
        preSave(user);
        containsUser(user.getId());
        return userStorage.update(user);
    }

    public void deleteUser(int id) {
        containsUser(id);
        userStorage.deleteUser(id);
    }

    public void addFriend(int userId, int friendId) {
        containsUser(userId);
        containsUser(friendId);
        userStorage.addFriend(userId, friendId);
        log.info(userId + " and " + friendId + " added to friends");
    }

    public void deleteFriend(int userId, int friendId) {
        containsUser(userId);
        containsUser(friendId);
        userStorage.deleteFriend(userId, friendId);
        log.info(userId + " and " + friendId + " removed from friends");
    }

    public List<User> getUserFriends(int userId) {
        containsUser(userId);
        return userStorage.getUserFriends(userId);
    }

    public List<User> getMutualFriends(int userId, int otherUserId) {
        containsUser(userId);
        containsUser(otherUserId);
        return userStorage.getMutualFriends(userId, otherUserId);
    }

    private void preSave(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("The name user field is set to login");
        }
    }

    private void containsUser(int id) {
        userStorage.get(id).orElseThrow(() -> new NotFoundException("User not found"));
    }
}