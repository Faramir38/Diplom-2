package ru.yandex.praktikum.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//POJO для изменения пользователя
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserBean {

    private String email;
    private String name;
}
