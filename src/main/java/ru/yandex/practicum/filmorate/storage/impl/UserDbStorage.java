package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.mapper.UserMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }

    @Override
    public List<User> findAllUsers() {
        final String sqlQuery = "SELECT * FROM users";

        log.info("List of users sent");
        return jdbcTemplate.query(sqlQuery, userMapper);
    }

    @Override
    public User createUser(User user) {
        final String sqlQuery = "INSERT INTO users (EMAIL, LOGIN, NAME, BIRTHDAY) " +
                "VALUES ( ?, ?, ?, ?)";
        KeyHolder generatedId = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, generatedId);
        log.info("User with id {} sent", user.getId());
        user.setId(Objects.requireNonNull(generatedId.getKey()).intValue());
        return user;
    }

    @Override
    public User update(User user) {
        final String sqlQuery = "UPDATE users " +
                "SET name = ?, login = ?, email = ?, birthday = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());
        log.info("User {} updated", user.getId());
        return user;
    }

    @Override
    public void deleteUser(int userId) {
        final String sqlQuery = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sqlQuery, userId);
        log.info("User with id {} deleted", userId);
    }

    @Override
    public Optional<User> get(int userId) {
        try {
            final String sqlQuery = "SELECT * FROM users WHERE id = ?";
            return Optional.of(jdbcTemplate.queryForObject(sqlQuery, userMapper, userId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void addFriend(int firstId, int secondId) {
        final String sqlQuery = "INSERT INTO friends (user_id, friend_id) " +
                "VALUES (?, ?)";
        String checkDuplicate = "SELECT * FROM friends WHERE user_id = ? AND friend_id = ?";
        SqlRowSet checkRows = jdbcTemplate.queryForRowSet(checkDuplicate, firstId, secondId);
        if (!checkRows.next()) {
            jdbcTemplate.update(sqlQuery, firstId, secondId);
            log.info("User {} subscribed to {}", firstId, secondId);
        }
    }

    @Override
    public void deleteFriend(int firstId, int secondId) {
        final String sqlQuery = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, firstId, secondId);
        log.info("User {} unfollowed {}", firstId, secondId);
    }

    @Override
    public List<User> getUserFriends(int id) {
        final String sqlQuery = "SELECT u.id, u.email, u.name, u.login, u.birthday " +
                "FROM friends AS f LEFT JOIN users AS u " +
                "ON f.friend_id = u.id WHERE f.user_id = ?" +
                "ORDER BY u.id";
        log.info("Request to get the list of friends of user {} completed", id);
        return jdbcTemplate.query(sqlQuery, userMapper, id);
    }

    @Override
    public List<User> getMutualFriends(int firstId, int secondId) {
        final String sqlQuery = "SELECT u.id, u.name, u.email, u.login, u.birthday " +
                "FROM friends as f " +
                "LEFT JOIN users AS u ON f.friend_id = u.id " +
                "WHERE f.user_id = ? " +
                "AND f.friend_id IN (SELECT friend_id FROM friends WHERE user_id = ?) ";
        log.info("List of mutual friends {} and {} sent", firstId, secondId);
        return jdbcTemplate.query(sqlQuery, userMapper, firstId, secondId);
    }
}