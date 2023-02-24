package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
@RequiredArgsConstructor

public class User extends Model {
    protected String name;
    private String email;
    private String login;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;


    public User(int id, String email, String name, String login, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.login = login;
        this.birthday = birthday;
    }
}