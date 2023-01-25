package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserControllerTest {

    private UserController userController;
    private User user;

    @BeforeEach
    public void createForAllTestUserControllerAndUser(){
        userController = new UserController();
        user = new User();
        user.setName("Serg");
        user.setEmail("postidemail@mail.ru");
        user.setLogin("Serhio");
        LocalDate localData = LocalDate.of(1985,5,27);
        user.setBirthday(localData);

    }
    @Test
    public void shouldPostUserIdTest(){
       ResponseEntity<User> responseEntity = userController.create(user);
        Assertions.assertEquals(1,responseEntity.getBody().getId());
    }
    @Test
    public void shouldPutUserIdTest(){
        ResponseEntity<User> responseEntity1 = userController.create(user);
        ResponseEntity<User> responseEntity2 = userController.update(user);
        int cod1 = responseEntity2.getStatusCodeValue();

        Assertions.assertEquals(200,cod1);

        user.setId(333);
        ResponseEntity<User> responseEntity3 = userController.update(user);
        int cod2 = responseEntity3.getStatusCodeValue();
        Assertions.assertEquals(500,cod2);

        user.setId(0);
        ResponseEntity<User> responseEntity4 = userController.update(user);
        int cod3 = responseEntity4.getStatusCodeValue();
        Assertions.assertEquals(500,cod3);

    }
    @Test
    public void shouldPostUserNameTest(){
        ResponseEntity<User> responseEntity = userController.create(user);
        Assertions.assertEquals("Serg",responseEntity.getBody().getName());

        user.setName(null);
        ResponseEntity<User> responseEntity2 = userController.create(user);

        Assertions.assertEquals("Serhio",responseEntity2.getBody().getName());
    }
    @Test
    public void shouldPutUserNameTest(){
        ResponseEntity<User> responseEntity = userController.create(user);

        User user1 = new User();
        user1.setId(1);
        user1.setEmail("postidemail@mail.ru");
        user1.setLogin("Serhio");
        LocalDate localData = LocalDate.of(1985,5,27);
        user1.setBirthday(localData);

        ResponseEntity<User> responseEntity1 = userController.update(user1);

        Assertions.assertEquals("Serg",responseEntity1.getBody().getName());
    }

}
