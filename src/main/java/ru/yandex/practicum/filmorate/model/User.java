package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
public class User {

    private int id;

    @Email(message = "Email cannot be empty and must contain the @ symbol")
    private String email;

    @NotBlank(message = "Login cannot be empty or contain spaces")
    @Pattern(regexp = ".*\\S.", message = "Login must not contain spaces")
    private String login;

    private String name;

    @PastOrPresent(message = "Date of birth cannot be in the future")
    private LocalDate birthday;
}