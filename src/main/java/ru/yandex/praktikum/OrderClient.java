package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.pojo.OrderBean;

import static io.restassured.RestAssured.given;


//реализация взаимодействия с Orders (получение списка заказов, создание заказа)
public class OrderClient extends BaseClient {

    @Step("Создание заказа (POST /api/orders)")
    public static Response createOrder(OrderBean orderBean, String accessToken) {
        return given(requestSpecification)
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .and()
                .body(orderBean)
                .when()
                .post(BurgerConst.BURGER_API_CREATE_ORDER);
    }

    @Step("Запрос заказов пользователя (GET /api/orders)")
    public static Response getUserOrdersList(String accessToken) {
        return given(requestSpecification)
                .auth().oauth2(accessToken)
                .get(BurgerConst.BURGER_API_VIEW_ORDER);
    }
}
