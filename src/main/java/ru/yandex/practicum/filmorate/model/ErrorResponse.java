package ru.yandex.practicum.filmorate.model;

public class ErrorResponse {
    int error;
    String description;

    public ErrorResponse(int error, String description) {
        this.error = error;
        this.description = description;
    }

    public ErrorResponse(String description) {
        this.description = description;
    }

    public int getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }
}