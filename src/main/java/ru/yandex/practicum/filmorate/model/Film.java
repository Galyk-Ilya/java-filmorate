package ru.yandex.practicum.filmorate.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Film extends Model {

    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private long duration;
    protected String name;


    public Film(int id, String description, String name, LocalDate releaseDate, long duration) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }
}
