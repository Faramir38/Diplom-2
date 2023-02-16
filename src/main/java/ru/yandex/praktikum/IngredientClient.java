package ru.yandex.praktikum;

import io.qameta.allure.Step;
import ru.yandex.praktikum.pojo.IngredientBean;
import ru.yandex.praktikum.pojo.IngredientListBean;

import java.util.List;

import static io.restassured.RestAssured.given;

//реализация взаимодействия с Ingredients (получение списка ингредиентов)
public class IngredientClient extends BaseClient{

    @Step("Запрос списка ингредиентов (GET /api/ingredients)")
    public static List<IngredientBean> getIngredientList() {
        return given(requestSpecification)
                .get(BurgerConst.BURGER_API_INGREDIENTS)
                .body().as(IngredientListBean.class).getData();
    }

}
