package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class FilmControllerTest {

   private FilmController filmController;
   private Film film;
   static LocalDate localDate;

   @BeforeEach
   public void createForAllTestUserControllerAndUser(){
      filmController = new FilmController();
      film = new Film();
      film.setName("Халк");
      film.setDescription("Доктор Бэннер подвергся воздействию гамма-лучей и превратился в Халка " +
              "- существо невероятной физической силы");
      LocalDate localData = LocalDate.of(2003,6,17);
      film.setReleaseDate(localData);
      film.setDuration(138);
   }

   @Test
   public void shouldIdPostFilTmest(){
      ResponseEntity<Film> responseEntity = filmController.create(film);
      Assertions.assertEquals(1,responseEntity.getBody().getId());
   }

   @Test
   public void shouldIdPutUserTest(){
      ResponseEntity<Film> responseEntity1 = filmController.create(film);
      ResponseEntity<Film> responseEntity2 = filmController.update(film);
      int cod1 = responseEntity2.getStatusCodeValue();

      Assertions.assertEquals(200,cod1);

      film.setId(333);
      ResponseEntity<Film> responseEntity3 = filmController.update(film);
      int cod2 = responseEntity3.getStatusCodeValue();
      Assertions.assertEquals(500,cod2);

      film.setId(0);
      ResponseEntity<Film> responseEntity4 = filmController.update(film);
      int cod3 = responseEntity4.getStatusCodeValue();
      Assertions.assertEquals(500,cod3);

   }
   @Test
   public void shouldreleaseDatePostUserTest(){
      ResponseEntity<Film> responseEntity = filmController.create(film);
      int cod = responseEntity.getStatusCodeValue();
      Assertions.assertEquals(200,cod);
   }
   @Test
   public void shouldBeforeReleaseDatePutUserTest(){

      localDate = LocalDate.of(1895,12,27);
      film.setReleaseDate(localDate);
      ResponseEntity<Film> responseEntity = filmController.create(film);
      int cod = responseEntity.getStatusCodeValue();
      Assertions.assertEquals(500,cod);

   }
   @Test
   public void shouldDataReleaseDatePutUserTest(){

      localDate = LocalDate.of(1895,12,28);
      film.setReleaseDate(localDate);
      ResponseEntity<Film> responseEntity = filmController.create(film);
      int cod = responseEntity.getStatusCodeValue();
      Assertions.assertEquals(200,cod);

   }
   @Test
   public void shouldAfterReleaseDatePutUserTest(){

      localDate = LocalDate.of(1895,12,29);
      film.setReleaseDate(localDate);
      ResponseEntity<Film> responseEntity = filmController.create(film);
      int cod = responseEntity.getStatusCodeValue();
      Assertions.assertEquals(200,cod);

   }

}
