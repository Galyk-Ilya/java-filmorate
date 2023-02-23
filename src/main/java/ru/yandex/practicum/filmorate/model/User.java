package ru.yandex.practicum.filmorate.model;

import lombok.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor(force=true)

public class User extends Model{
    private String email;
    private String login;

    private String name;
    private LocalDate birthday;

    public User(int id, String email, String name, String login, String birthday) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.login = login;
        parseLocalDate(birthday);
    }

    private void parseLocalDate(String birthdayString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.birthday = LocalDate.parse(birthdayString, formatter);
        //Долго не мог обойти ошибку jsona на вход LocalDate поля, так и не нашел решение =\
    }
}