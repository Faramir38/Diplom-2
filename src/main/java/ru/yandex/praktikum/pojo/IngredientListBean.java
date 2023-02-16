package ru.yandex.praktikum.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//POJO для ответа на запрос списка ингредиентов
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class IngredientListBean {

    private boolean success;
    private List<IngredientBean> data;

}
