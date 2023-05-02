package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
public class User{

    private int id;

    @Email(message = "Email cannot be empty and must contain the @ symbol")
    private String email;

    private String name;

    @NotBlank(message = "Login cannot be empty or contain spaces")
    private String login;

    @Past(message = "user passed the test for Birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}