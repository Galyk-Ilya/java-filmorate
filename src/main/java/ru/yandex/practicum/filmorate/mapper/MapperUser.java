package ru.yandex.practicum.filmorate.mapper;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class MapperUser implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder().id(rs.getInt("id"))
                .email(rs.getString("email"))
                .name(rs.getString("name"))
                .login(rs.getString("login"))
                .birthday(rs.getDate("birthsday").toLocalDate()).build();
    }
}
