<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
<a name="readme-top"></a>
<div align="center">
<h2 align="center">Yandex Practicum (QA Java) Diplom part 2 (API)</h2>
  <p align="center">
    Вторая часть дипломного проекта Яндекс Практикум по специальности 
    "Автоматизатор тестирования на Java". <br /> Тестирование API. 
    <br />
    <a href="https://github.com/Faramir38/Diplom-2"><strong>Explore the docs »</strong></a>
    <br />
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About The Project

**Задание:** Тебе нужно протестировать ручки API 
для <a href="https://stellarburgers.nomoreparties.site/">Stellar Burgers</a>.<br/>
Пригодится <a href="https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf">
документация API</a>. 
В ней описаны все ручки сервиса. Тестировать нужно только те, которые указаны в задании. 
Всё остальное — просто для контекста. <br/>
**Создание пользователя:**
- создать уникального пользователя;
- создать пользователя, который уже зарегистрирован;
- создать пользователя и не заполнить одно из обязательных полей.

**Логин пользователя:**
- логин под существующим пользователем,
- логин с неверным логином и паролем.

**Изменение данных пользователя:**
- с авторизацией,
- без авторизации,

Для обеих ситуаций нужно проверить, что любое поле можно изменить. Для неавторизованного пользователя — ещё и то, что система вернёт ошибку.

**Создание заказа:**
- с авторизацией,
- без авторизации,
- с ингредиентами,
- без ингредиентов,
- с неверным хешем ингредиентов.

**Получение заказов конкретного пользователя:**
- авторизованный пользователь,
- неавторизованный пользователь.

**Что нужно сделать** <br/>
- Создай отдельный репозиторий для тестов API.
- Создай Maven-проект.
- Подключи JUnit 4, RestAssured и Allure.
- Напиши тесты.
- Сделай отчёт в Allure.
<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

* <a href="https://www.java.com/ru/">Java 11</a>
* <a href="https://junit.org/junit4/">Junit 4</a>
* <a href="https://rest-assured.io/">RestAssured</a>
* <a href="https://github.com/allure-framework">Allure</a>
* <a href="https://projectlombok.org/">Lombok</a>
* <a href="https://www.datafaker.net/">Datafaker</a>

<p align="right">(<a href="#readme-top">back to top</a>)</p>
<!-- CONTACT -->

## Contact

Дмитрий Носко - [@faramir38](https://t.me/faramir38) - dmitry.nosko@gmail.com

Project Link: [https://github.com/Faramir38/Diplom-1](https://github.com/Faramir38/Diplom-1)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

