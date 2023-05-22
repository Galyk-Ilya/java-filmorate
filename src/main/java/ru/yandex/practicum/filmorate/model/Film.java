package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
public class Film {

    private int id;

    @NotBlank(message = "Movie title cannot be empty")
    private String name;

    @Size(min = 1, max = 200, message = "The maximum description length is 200 characters")
    private String description;

    private LocalDate releaseDate;

    @Positive(message = "Movie duration must be positive")
    private long duration;

    private List<Genre> genres;

    private Mpa mpa;
}