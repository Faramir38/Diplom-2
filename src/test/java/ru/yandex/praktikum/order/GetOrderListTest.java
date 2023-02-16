package ru.yandex.praktikum.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.praktikum.*;
import ru.yandex.praktikum.pojo.IngredientBean;
import ru.yandex.praktikum.pojo.NewUserBean;
import ru.yandex.praktikum.pojo.OrderBean;
import ru.yandex.praktikum.pojo.UserLoginBean;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderListTest extends BaseTest {

    //авторизованный пользователь
    @Test
    @DisplayName("GET /api/orders возвращает список заказов для авторизованного пользователя")
    public void shouldListOrdersForAuthUser(){
        //Arrange
        NewUserBean newUser = new NewUserBean(DataGenerator.randomEmail(),
                DataGenerator.randomPassword(), DataGenerator.randomName());
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }
        //логинимся
        UserClient.loginUser(new UserLoginBean(newUser.getEmail(), newUser.getPassword()));

        //получаем список хешей ингредиентов
        List<IngredientBean> ingredientList = IngredientClient.getIngredientList();
        //формируем список ингредиентов для заказа
        List<String> ingredients = List.of(ingredientList.get(0).get_id(),
                ingredientList.get(1).get_id(),
                ingredientList.get(2).get_id());
        //создаем заказ
        OrderClient.createOrder(new OrderBean(ingredients),getTokenToDelete());

        //Act
        OrderClient.getUserOrdersList(getTokenToDelete())

                //Assert
                .then().statusCode(SC_OK)
                .and().assertThat().body("success", equalTo(true))
                .and().assertThat().body("orders", notNullValue())
                .and().assertThat().body("total", notNullValue())
                .and().assertThat().body("totalToday", notNullValue());

    }

    //неавторизованный пользователь
    @Test
    @DisplayName("GET /api/orders возвращает 401 для неавторизованного пользователя")
    public void shouldNotListOrdersForUnauthUser(){
        //Arrange
        NewUserBean newUser = new NewUserBean(DataGenerator.randomEmail(),
                DataGenerator.randomPassword(), DataGenerator.randomName());
        Response response = UserClient.registerUser(newUser);
        if (response.statusCode() == SC_OK) {
            setTokenToDelete(UserClient.getAccessToken(response));
        }
        String refreshToken = UserClient.getRefreshToken(response);
        //логинимся
        UserClient.loginUser(new UserLoginBean(newUser.getEmail(), newUser.getPassword()));

        //получаем список хешей ингредиентов
        List<IngredientBean> ingredientList = IngredientClient.getIngredientList();
        //формируем список ингредиентов для заказа
        List<String> ingredients = List.of(ingredientList.get(0).get_id(),
                ingredientList.get(1).get_id(),
                ingredientList.get(2).get_id());
        //создаем заказ
        OrderClient.createOrder(new OrderBean(ingredients),getTokenToDelete());
        //разлогиниваемся
        UserClient.logoutUser(refreshToken);

        //Act
        OrderClient.getUserOrdersList(getTokenToDelete())

                //Assert
                .then().statusCode(SC_UNAUTHORIZED)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("You should be authorised"));

    }

}
