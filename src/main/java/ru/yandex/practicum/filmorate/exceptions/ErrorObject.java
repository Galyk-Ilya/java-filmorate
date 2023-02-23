package ru.yandex.practicum.filmorate.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorObject {
    private Integer statusCode;
    private String message;
}
