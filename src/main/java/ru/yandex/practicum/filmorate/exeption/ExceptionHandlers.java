package ru.yandex.practicum.filmorate.exeption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorException exceptionHandler(ValidationException validationException) {
        log.debug(validationException.getMessage());
        return new ErrorException(System.currentTimeMillis(), validationException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorException exceptionHandler(Exception exception) {
        log.debug(exception.getMessage());
        return new ErrorException(System.currentTimeMillis(), exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorException exceptionHandler(NotFoundException notFoundException) {
        log.debug(notFoundException.getMessage());
        return new ErrorException(System.currentTimeMillis(), notFoundException.getMessage());
    }
}
