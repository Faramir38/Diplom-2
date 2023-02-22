package ru.yandex.praktikum;

import net.datafaker.Faker;

//Генерация случайных данных
public class DataGenerator {

    //случайный name
    public static String randomName() {
        Faker faker = new Faker();
        return faker.name().firstName();

    }

    //случайный password
    public static String randomPassword() {
        Faker faker = new Faker();
        return faker.text().text(6, 10);
    }

    //случайный email
    public static String randomEmail() {
        Faker faker = new Faker();
        return faker.internet().emailAddress();
    }
}
