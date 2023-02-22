package ru.yandex.praktikum.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//POJO для регистрации пользователя
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewUserBean {

    private String email;
    private String password;
    private String name;
}
