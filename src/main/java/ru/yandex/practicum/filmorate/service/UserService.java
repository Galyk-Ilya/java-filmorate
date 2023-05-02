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
        userStorage.addFriend(userId,friendId);
        log.info(userId + " and " + friendId + " added to friends");
    }

    public void deleteFriend(int userId, int friendId) {
        userStorage.deleteFriend(userId,friendId);
        log.info(userId + " and " + friendId + " removed from friends");
    }

    public List<User> getUserFriends(int userId) {
        return userStorage.getUserFriends(userId);
    }

    public List<User> getMutualFriends(int userId, int otherUserId) {
        return userStorage.getMutualFriends(userId,otherUserId);
    }

    private void preSave(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("The name user field is set to login");
        }
    }

//    public void addFriend(int id, int friendId) {
//        if (entities.get(id) == null || entities.get(friendId) == null) {
//            throw new NotFoundException("Not found id:" + id + " or " + friendId);
//        }
//        entities.get(id).getFriendsId().add(friendId);
//        entities.get(friendId).getFriendsId().add(id);
//        log.info(id + " and " + friendId + " added to friends");
//    }
//
//    public void deleteFriend(int id, int friendId) {
//        if (entities.get(id) == null || entities.get(friendId) == null) {
//            throw new NotFoundException("Not found id:" + id + " or " + friendId);
//        }
//        entities.get(id).getFriendsId().remove(friendId);
//        entities.get(friendId).getFriendsId().remove(id);
//        log.info(id + " and " + friendId + " removed from friends");
//    }
//
//    public List<User> getFriends(int id) {
//        return entities.get(id).getFriendsId().stream()
//                .map(integer -> entities.get(integer))
//                .collect(Collectors.toList());
//    }
//
//    public List<User> getMutualFriends(int id, int otherId) {
//        return entities.get(id).getFriendsId().stream()
//                .filter(integer -> entities.get(otherId).getFriendsId().contains(integer))
//                .map(integer -> entities.get(integer))
//                .collect(Collectors.toList());
//    }
}