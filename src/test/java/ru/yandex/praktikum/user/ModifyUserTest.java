package ru.yandex.praktikum.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import ru.yandex.praktikum.BaseTest;
import ru.yandex.praktikum.DataGenerator;
import ru.yandex.praktikum.UserClient;
import ru.yandex.praktikum.pojo.NewUserBean;
import ru.yandex.praktikum.pojo.UserBean;
import ru.yandex.praktikum.pojo.UserLoginBean;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class ModifyUserTest extends BaseTest {

    //Для обеих ситуаций нужно проверить, что любое поле можно изменить.
    // Для неавторизованного пользователя — ещё и то, что система вернёт ошибку.

    //с авторизацией
    @Test
    @DisplayName("PATCH /api/auth/user изменяет email авторизованного пользователя")
    public void shouldModifyEmailAuthorisedUser()
    {
        //Arrange
        String oldEmail = DataGenerator.randomEmail();
        NewUserBean newUser = new NewUserBean(oldEmail, DataGenerator.randomPassword(),
                DataGenerator.randomName());
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }
        UserClient.loginUser(new UserLoginBean(newUser.getEmail(), newUser.getPassword()));
        String newEmail = DataGenerator.randomEmail();

        //Act
        UserClient.modifyUser(new UserBean(newEmail, newUser.getName()), getTokenToDelete())
        //Assert
                .then().statusCode(SC_OK)
                .and().assertThat().body("success", equalTo(true))
                .and().assertThat().body("user", notNullValue());

        Assert.assertEquals("Email не изменился", newEmail,
                UserClient.getUserData(getTokenToDelete()).getEmail());

    }

    @Test
    @DisplayName("PATCH /api/auth/user изменяет name авторизованного пользователя")
    public void shouldModifyNameAuthorisedUser()
    {
        //Arrange
        String oldName = DataGenerator.randomName();
        NewUserBean newUser = new NewUserBean(DataGenerator.randomEmail(), DataGenerator.randomPassword(),
                oldName);
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }
        UserClient.loginUser(new UserLoginBean(newUser.getEmail(), newUser.getPassword()));
        String newName = DataGenerator.randomName();

        //Act
        UserClient.modifyUser(new UserBean(newUser.getEmail(), newName), getTokenToDelete())
                //Assert
                .then().statusCode(SC_OK)
                .and().assertThat().body("success", equalTo(true))
                .and().assertThat().body("user", notNullValue());

        Assert.assertEquals("Name не изменился", newName,
                UserClient.getUserData(getTokenToDelete()).getName());

    }

    //без авторизации
    @Test
    @DisplayName("PATCH /api/auth/user не изменяет email неавторизованного пользователя")
    public void shouldNotModifyEmailUnauthorisedUser()
    {
        //Arrange
        String oldEmail = DataGenerator.randomEmail();
        NewUserBean newUser = new NewUserBean(oldEmail, DataGenerator.randomPassword(),
                DataGenerator.randomName());
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }

        String newEmail = DataGenerator.randomEmail();

        //Act
        UserClient.modifyUser(new UserBean(newEmail, newUser.getName()), getTokenToDelete())

                //Assert
                .then().statusCode(SC_UNAUTHORIZED)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("You should be authorised"));

        Assert.assertEquals("Email изменился", oldEmail,
                UserClient.getUserData(getTokenToDelete()).getEmail());

    }

    @Test
    @DisplayName("PATCH /api/auth/user не изменяет name неавторизованного пользователя")
    public void shouldNotModifyNameUnauthorisedUser()
    {
        //Arrange
        String oldName = DataGenerator.randomName();
        NewUserBean newUser = new NewUserBean(DataGenerator.randomEmail(), DataGenerator.randomPassword(),
                oldName);
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }

        String newName = DataGenerator.randomName();

        //Act
        UserClient.modifyUser(new UserBean(newUser.getEmail(), newName), getTokenToDelete())
                //Assert
                .then().statusCode(SC_UNAUTHORIZED)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("You should be authorised"));

        Assert.assertEquals("Name изменился", oldName,
                UserClient.getUserData(getTokenToDelete()).getName());

    }

}
