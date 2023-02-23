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
public class Film extends Model{

    private String description;
    private String name;
    private LocalDate releaseDate;
    private long duration;

    public Film(int id, String description, String name, String releaseDate, long duration) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.duration = duration;
        parseLocalDate(releaseDate);
    }

    private void parseLocalDate(String birthdayString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.releaseDate = LocalDate.parse(birthdayString, formatter);
        //Долго не мог обойти ошибку json-a на вход LocalDate поля, так и не нашел решение =\
    }
}
