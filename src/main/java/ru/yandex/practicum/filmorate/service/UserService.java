package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService<User>{
    @Override
    protected void validate(User entity) {

    }

    public void addFriend(int id, int friendId) {
        entities.get(id).getFriendId().add(friendId);
        entities.get(friendId).getFriendId().add(id);
    }

    public void deleteFriend(int id, int friendId) {
        entities.get(id).getFriendId().remove(friendId);
        entities.get(friendId).getFriendId().remove(id);
    }

    public Set<Integer> getFriends(int id) {
        return entities.get(id).getFriendId();
    }

    public List<User> getMutualFriends(int id, int otherId) {
        return entities.get(id).getFriendId().stream().
                filter(integer -> entities.get(otherId).getFriendId().contains(integer)).
                map(integer -> entities.get(integer)).
                collect(Collectors.toList());
    }
}