package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
//    @ExceptionHandler
//    public ResponseEntity<ErrorObject> handleInputDataException(InputDataException exception) {
//        ErrorObject errorObject = new ErrorObject();
//        errorObject.setStatusCode(HttpStatus.UNAUTHORIZED.value());
//        errorObject.setMessage(exception.getMessage());
//        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.UNAUTHORIZED);
//    }
}
