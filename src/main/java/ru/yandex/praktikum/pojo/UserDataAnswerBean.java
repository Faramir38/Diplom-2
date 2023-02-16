package ru.yandex.praktikum.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//POJO для ответа на запрос данных пользователя
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserDataAnswerBean {

    boolean success;
    UserBean user;
}
