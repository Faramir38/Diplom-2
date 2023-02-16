package ru.yandex.praktikum.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//POJO для авторизации пользователя
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginBean {

    private String email;
    private String password;

}
