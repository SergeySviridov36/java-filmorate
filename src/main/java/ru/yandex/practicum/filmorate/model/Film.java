package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Film {

    private int id;
    @NotBlank
    private String name;
    @NotBlank
    @Size(min=1,max=200)
    private String description;
    @Past
    private LocalDate releaseDate;
    @Positive
    private int duration;
}