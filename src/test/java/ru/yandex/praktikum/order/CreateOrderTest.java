package ru.yandex.praktikum.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import ru.yandex.praktikum.*;
import ru.yandex.praktikum.pojo.IngredientBean;
import ru.yandex.praktikum.pojo.NewUserBean;
import ru.yandex.praktikum.pojo.OrderBean;
import ru.yandex.praktikum.pojo.UserLoginBean;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest extends BaseTest {

    //с авторизацией
    @Test
    @DisplayName("GET /api/orders возвращает список заказов для авторизованного пользователя")
    public void shouldCreateOrderForAuthUser(){
        //Arrange
        NewUserBean newUser = new NewUserBean(DataGenerator.randomEmail(),
                DataGenerator.randomPassword(), DataGenerator.randomName());
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }
        UserClient.loginUser(new UserLoginBean(newUser.getEmail(), newUser.getPassword()));

        //получаем список хешей ингредиентов
        List<IngredientBean> ingredientList = IngredientClient.getIngredientList();
        //формируем список ингредиентов для заказа
        List<String> ingredients = List.of(ingredientList.get(0).get_id(),
                ingredientList.get(1).get_id(),
                ingredientList.get(2).get_id());

        //Act
        OrderClient.createOrder(new OrderBean(ingredients),getTokenToDelete())

                //Assert
                .then().statusCode(SC_OK)
                .and().assertThat().body("success", equalTo(true))
                .and().assertThat().body("name", notNullValue())
                .and().assertThat().body("order", notNullValue());

    }

    //без авторизации
    @Test
    @DisplayName("POST /api/orders не создает заказ для неавторизованного пользователя")
    public void shouldNotCreateOrderForUnauthUser(){
        //Arrange
        NewUserBean newUser = new NewUserBean(DataGenerator.randomEmail(),
                DataGenerator.randomPassword(), DataGenerator.randomName());
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }

        //получаем список хешей ингредиентов
        List<IngredientBean> ingredientList = IngredientClient.getIngredientList();
        //формируем список ингредиентов для заказа
        List<String> ingredients = List.of(ingredientList.get(0).get_id(),
                ingredientList.get(1).get_id(),
                ingredientList.get(2).get_id());

        //Act
        int actualStatus = OrderClient.createOrder(new OrderBean(ingredients),getTokenToDelete()).getStatusCode();

        //Assert
        Assert.assertNotEquals("Статус ответа на запрос не 4хх", SC_OK, actualStatus);
    }

    //без ингредиентов
    @Test
    @DisplayName("POST /api/orders не создает заказ без ингредиентов и возвращает 400")
    public void shouldNotCreateOrderWithoutIngredient(){
        //Arrange
        NewUserBean newUser = new NewUserBean(DataGenerator.randomEmail(),
                DataGenerator.randomPassword(), DataGenerator.randomName());
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }
        UserClient.loginUser(new UserLoginBean(newUser.getEmail(), newUser.getPassword()));

        //формируем список ингредиентов для заказа
        List<String> ingredients = List.of();

        //Act
        OrderClient.createOrder(new OrderBean(ingredients),getTokenToDelete())

                //Assert
                .then().statusCode(SC_BAD_REQUEST)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("Ingredient ids must be provided"));


    }


    //с неверным хешем ингредиентов
    @Test
    @DisplayName("POST /api/orders не создает заказ с неверным id ингредиента и возвращает 500")
    public void shouldNotCreateOrderWithWrongIds(){
        //Arrange
        NewUserBean newUser = new NewUserBean(DataGenerator.randomEmail(),
                DataGenerator.randomPassword(), DataGenerator.randomName());
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }
        UserClient.loginUser(new UserLoginBean(newUser.getEmail(), newUser.getPassword()));

        //формируем список ингредиентов для заказа
        List<String> ingredients = List.of(DataGenerator.randomPassword());

        //Act
        OrderClient.createOrder(new OrderBean(ingredients),getTokenToDelete())

                //Assert
                .then().statusCode(SC_INTERNAL_SERVER_ERROR);


    }

}
