package ru.yandex.practicum.filmorate.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorException {

    private long timeException;

    private String messageException;

}
