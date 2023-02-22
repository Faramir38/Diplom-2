package ru.yandex.praktikum.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//POJO для создания заказа
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderBean {

    private List<String> ingredients;
}
