package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    List<User> findAllUsers();

    User createUser(User user);

    User update(User user);

    User deleteUser(int id);

    User get(int userId);

    void addFriend(int firstId, int secondId);

    void deleteFriend(int firstId, int secondId);

    List<User> getUserFriends(int id);

    List<User> getMutualFriends(int firstId, int secondId);

}
