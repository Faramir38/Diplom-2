package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.pojo.*;

import static io.restassured.RestAssured.given;

//реализация взаимодействия с User (авторизация, регистрация и пр)
public class UserClient extends BaseClient {

    @Step("Регистрация пользователя (POST /api/auth/register)")
    public static Response registerUser(NewUserBean newUser) {
        return given(requestSpecification)
                .header("Content-type", "application/json")
                .and()
                .body(newUser)
                .when()
                .post(BurgerConst.BURGER_API_USER_REGISTER);
    }

    @Step("Извлечение accessToken")
    public static String getAccessToken(Response response) {

        return response.body().as(AuthAnswerBean.class).getAccessToken().replace("Bearer ", "");
    }

    @Step("Извлечение refreshToken")
    public static String getRefreshToken(Response response) {
        return response.body().as(AuthAnswerBean.class).getRefreshToken();
    }

    @Step("Удаление пользователя (DELETE /api/auth/user)")
    public static Response deleteUser(String accessToken) {
        return given(requestSpecification)
                .auth().oauth2(accessToken)
                .delete(BurgerConst.BURGER_API_USER_DELETE);
    }

    @Step("Авторизация пользователя (POST /api/auth/login)")
    public static Response loginUser(UserLoginBean userLogin) {
        return given(requestSpecification)
                .header("Content-type", "application/json")
                .and()
                .body(userLogin).when()
                .post(BurgerConst.BURGER_API_USER_AUTH);
    }

    @Step("Изменение данных пользователя (PATCH /api/auth/user)")
    public static Response modifyUser(UserBean user, String accessToken) {
        return given(requestSpecification)
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .and()
                .body(user).when()
                .patch(BurgerConst.BURGER_API_USER_MODIFY);
    }

    @Step("Получение данных пользователя (GET /api/auth/user)")
    public static UserBean getUserData(String accessToken) {
        return given(requestSpecification)
                .auth().oauth2(accessToken)
                .get(BurgerConst.BURGER_API_USER_DATA).body().as(UserDataAnswerBean.class).getUser();
    }

    @Step("Выход из системы (POST /api/auth/logout)")
    public static Response logoutUser(String refreshToken) {
        return given(requestSpecification)
                .header("Content-type", "application/json")
                .and()
                .body(String.format("{\"token\": \"{{%s}}\"}", refreshToken)).when()
                .post(BurgerConst.BURGER_API_USER_LOGOUT);

    }
}
