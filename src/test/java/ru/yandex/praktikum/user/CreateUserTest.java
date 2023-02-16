package ru.yandex.praktikum.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.praktikum.BaseTest;
import ru.yandex.praktikum.DataGenerator;
import ru.yandex.praktikum.UserClient;
import ru.yandex.praktikum.pojo.NewUserBean;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateUserTest extends BaseTest {

    //создать уникального пользователя
    @Test
    @DisplayName("POST /api/auth/register возвращает 200 и токены при успешном создании пользователя")
    public void shouldCreateNewUser() {
        //Arrange
        NewUserBean newUser = new NewUserBean(DataGenerator.randomEmail(), DataGenerator.randomPassword(),
                DataGenerator.randomName());

        //Act
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }

        //Assert
        response.then().statusCode(SC_OK)
                .and().assertThat().body("success", equalTo(true))
                .and().assertThat().body("user", notNullValue())
                .and().assertThat().body("accessToken", notNullValue())
                .and().assertThat().body("refreshToken", notNullValue());


    }

    //создать пользователя, который уже зарегистрирован
    @Test
    @DisplayName("POST /api/auth/register возвращает 403 при попытке создать уже созданного пользователя")
    public void shouldNotCreateExistUser() {
        //Arrange
        NewUserBean newUser = new NewUserBean(DataGenerator.randomEmail(), DataGenerator.randomPassword(),
                DataGenerator.randomName());

        //Act
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }
        Response response1 = UserClient.registerUser(newUser);
        //на всякий случай помечаем на удаление, если пользователь всё-таки создан вторично
        if (response1.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }

        //Assert
        response1.then().statusCode(SC_FORBIDDEN)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo( "User already exists"));

    }
    //создать пользователя и не заполнить одно из обязательных полей (параметризованный)
    @Test
    @DisplayName("POST /api/auth/register возвращает 403 при попытке создания пользователя без email")
    public void shouldNotCreateUserWithoutEmail() {
        //Arrange
        NewUserBean newUser = new NewUserBean("", DataGenerator.randomPassword(),
                DataGenerator.randomName());

        //Act
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }

        //Assert
        response.then().statusCode(SC_FORBIDDEN)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo( "Email, password and name are required fields"));

    }

    @Test
    @DisplayName("POST /api/auth/register возвращает 403 при попытке создания пользователя без password")
    public void shouldNotCreateUserWithoutPassword() {
        //Arrange
        NewUserBean newUser = new NewUserBean(DataGenerator.randomEmail(), "",
                DataGenerator.randomName());

        //Act
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }

        //Assert
        response.then().statusCode(SC_FORBIDDEN)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo( "Email, password and name are required fields"));

    }


    @Test
    @DisplayName("POST /api/auth/register возвращает 403 при попытке создания пользователя без name")
    public void shouldNotCreateUserWithoutName() {
        //Arrange
        NewUserBean newUser = new NewUserBean(DataGenerator.randomEmail(), DataGenerator.randomPassword(),
                "");

        //Act
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }

        //Assert
        response.then().statusCode(SC_FORBIDDEN)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo( "Email, password and name are required fields"));

    }

}
