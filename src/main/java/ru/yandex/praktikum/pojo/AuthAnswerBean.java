package ru.yandex.praktikum.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//POJO для ответа на запрос регистрации и авторизации пользователя
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthAnswerBean {

    private boolean success;
    private UserBean user;
    private String accessToken;
    private String refreshToken;
}
