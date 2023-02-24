package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString


public class User extends Model {
    protected String name;
    private String email;
    private String login;

 //   @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;


//    public User(int id, String email, String name, String login, LocalDate birthday) {
//        this.id = id;
//        this.email = email;
//        this.name = name;
//        this.login = login;
//        this.birthday = birthday;
//    }

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
    }
}