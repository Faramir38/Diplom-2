package ru.yandex.praktikum;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.Setter;
import org.junit.After;

//базовый тестовый класс для удаления созданных пользователей
@Getter
@Setter
public class BaseTest {

    private String tokenToDelete = null;

    @After
    @Step("Удаление созданного курьера")
    public void deleteCreatedUser() {

        if (tokenToDelete != null) {
            UserClient.deleteUser(tokenToDelete);
        }
    }
}
