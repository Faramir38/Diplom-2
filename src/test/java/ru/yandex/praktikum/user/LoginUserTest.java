package ru.yandex.praktikum.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.praktikum.BaseTest;
import ru.yandex.praktikum.DataGenerator;
import ru.yandex.praktikum.UserClient;
import ru.yandex.praktikum.pojo.NewUserBean;
import ru.yandex.praktikum.pojo.UserLoginBean;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginUserTest extends BaseTest {

    //логин под существующим пользователем
    @Test
    @DisplayName("POST /api/auth/login возвращает 200 и токены при успешной авторизации")
    public void shouldLoginWithRegisteredUser() {
        //Arrange
        NewUserBean newUser = new NewUserBean(DataGenerator.randomEmail(), DataGenerator.randomPassword(),
                DataGenerator.randomName());
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }

        //Act
        UserClient.loginUser(new UserLoginBean(newUser.getEmail(), newUser.getPassword()))

                //Assert
                .then().statusCode(SC_OK)
                .and().assertThat().body("success", equalTo(true))
                .and().assertThat().body("user", notNullValue())
                .and().assertThat().body("accessToken", notNullValue())
                .and().assertThat().body("refreshToken", notNullValue());
    }

    //логин с неверным логином и паролем
    @Test
    @DisplayName("POST /api/auth/login возвращает 401 при авторизации с неверным паролем")
    public void shouldNotLoginWithIncorrectPassword() {
        //Arrange
        NewUserBean newUser = new NewUserBean(DataGenerator.randomEmail(), DataGenerator.randomPassword(),
                DataGenerator.randomName());
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }

        //Act
        UserClient.loginUser(new UserLoginBean(newUser.getEmail(), DataGenerator.randomPassword()))

                //Assert
                .then().statusCode(SC_UNAUTHORIZED)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("email or password are incorrect"));
    }


    @Test
    @DisplayName("POST /api/auth/login возвращает 401 при авторизации с неверным email")
    public void shouldNotLoginWithIncorrectEmail() {
        //Arrange
        NewUserBean newUser = new NewUserBean(DataGenerator.randomEmail(), DataGenerator.randomPassword(),
                DataGenerator.randomName());
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }

        //Act
        UserClient.loginUser(new UserLoginBean(DataGenerator.randomEmail(), newUser.getPassword()))

                //Assert
                .then().statusCode(SC_UNAUTHORIZED)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("email or password are incorrect"));
    }
}
