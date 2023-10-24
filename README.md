# Libera

[[_TOC_]]
 
# Authorization Server

## Общая информация

Authorization Server - является единым сервером авторизации для всех сервисов и модулей ERP системы.

Для авторизации пользователей используем JWT.

Одно из преимуществ аутентификации с использованием JWT – это возможность выделить выдачу токенов и работу с данными пользователей в отдельное приложение, переложив механизм валидации токенов на клиентские приложения. Этот механизм отлично подходит микросервисной архитектуре.

Сделаем несколько приложений: одно будет отвечать за выдачу токенов (сервер авторизации), а другое содержать бизнес логику. Сервис с бизнес логикой не сможет выдавать токены, но сможет их валидировать. Таких приложений может быть много.
## Чтобы подключить бд к проекту зайдите SQL Shell:
Server [localhost]:
Database [postgres]:
Port [5432]:
Username [postgres]:
Пароль пользователя postgres: root
###Дальше для создания базы данных напишите:  
create database api;
###Подрубаем расширение
create extension if not exists pgcrypto;(С помощью расширений можно обработать временные, пространственные и другие типы данных)
## Механизм аутентификации

1. Клиент API, чаще всего это фронт, присылает объект с логином и паролем.
2. Если пароль подходит, то генерируется access и refresh токены и отправляются в ответ.
3. Клиент API использует access токен для взаимодействия с остальными точками входа API.
4. Через пять минут, когда access токен протухает, фронт отправляет refresh токен и получает новый access токен. И так по кругу, пока не протухнет refresh токен.
5. Refresh токен выдается на 30 дней. Примерно на 25-29 день клиент API отправляет валидный refresh токен вместе с валидным access токеном и взамен получает новую пару токенов.

## Роли пользователей

1. ADMIN - администратор. Обладает всеми правами: `USERS_READ, USERS_WRITE`.
2. USER - обычный пользователь. Обладает всеми правами: `USERS_READ`.

## Работа с API

### Авторизация

Request url:

`POST: /api/auth/login`

Request body: 
```json
{
    "login": "login",
    "password": "pass"
}
```

Response body:
```json
{
    "type": "Bearer",
    "accessToken": "qBTmv4oXFFR2GwjexDJ4t6fsIUIUhhXqlktXjXdkcyygs8nPVEwMfo29VDRRepYDVV5IkIxBMzr7OEHXEHd37w==",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4NjU1MTUyOX0.xoZMisD75zKyf_7eC_5v12F9L7wLCWQuLW6gpl5d8xj68FKyjWWm4HYzW5Sc9ls96lqo_3x4W___WX6wtqIdfg"
}
```
### Получение нового acces token

Request url:

`POST: /auth/api/auth/token`

Request body:
```json
{
"refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4NDM4OTIxNH0.EkP5_PvTKjU7zav7aHTALcIGXDnS6H7XUbK8NMFHaFjt_613bNyf4INT6760s7669A0RZ1z7uR-vHQtyHbcgtQ"
}
```

Response body:
```json
{
    "type": "Bearer",
    "accessToken": "qBTmv4oXFFR2GwjexDJ4t6fsIUIUhhXqlktXjXdkcyygs8nPVEwMfo29VDRRepYDVV5IkIxBMzr7OEHXEHd37w==",
    "refreshToken": null
}
```
### Получение новых access и refresh токенов взамен текущего refresh токена
Request url:

`POST: /auth/api/auth/refresh`

Request body:
```json
{
"refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4NDM4OTIxNH0.EkP5_PvTKjU7zav7aHTALcIGXDnS6H7XUbK8NMFHaFjt_613bNyf4INT6760s7669A0RZ1z7uR-vHQtyHbcgtQ"
}
```

Также не стоит забывать, что это защищенный метод, поэтому в Authorization вставляем access токен

Response body:
```json
{
    "type": "Bearer",
    "accessToken": "qBTmv4oXFFR2GwjexDJ4t6fsIUIUhhXqlktXjXdkcyygs8nPVEwMfo29VDRRepYDVV5IkIxBMzr7OEHXEHd37w==",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4NjU1MzUwNH0.jF8bJCuq6LdM4oJRSuZVW6oRI89NCTrqWziqsV-iABCPPiiKJ9z14tZJN3tJJJ5q2FV0PsHvSnrp2oEmBYWHww"
}
```

### Выход из сессии пользователя
Request url:

`POST: /auth/api/auth/logout`

Request body:
```json
{
"refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4NDM4OTIxNH0.EkP5_PvTKjU7zav7aHTALcIGXDnS6H7XUbK8NMFHaFjt_613bNyf4INT6760s7669A0RZ1z7uR-vHQtyHbcgtQ"
}
```

Также не стоит забывать, что это защищенный метод, поэтому в Authorization вставляем access токен

# LibraryService##
## Общая информация 
Веб-сервисы (или веб-службы) — это технология, позволяющая системам обмениваться данными друг с другом через сетевое подключение. Обычно веб-сервисы работают поверх протокола HTTP или протокола более высокого уровня.
Веб-сервис — просто адрес, ссылка, обращение к которому позволяет получить данные или выполнить действие.
Главное отличие веб-сервиса от других способов передачи данных: стандартизированность. Приняв решение использовать веб-сервисы, можно сразу переходить к структуре данных и доступным функциям.
В данном проекте я использовал : REST (Representational State Transfer) — архитектурный стиль взаимодействия компьютерных систем в сети основанный на методах протокола HTTP, 
так же тут я расписал работу самой бибилиотеки 

## Main entities
Author
Book
LibraryUser


### Действия с Author
Request url:
`Get: /api/v1/author`:
Даёт нам все данные о всх Author

Request url:
`Get: /api/v1/author/{id}`:
Даёт нам все данные конкретного Author

Request url:
`Post: /api/v1/author/{id}`:
Добавление Author

Request url:
`Put: /api/v1/author/{id}`:
Изменение данных Author

Request url:
`Delete: /api/v1/author/{id}`:
Удаление орпеделённого Author


### Действия с Book
Request url:
`Get: /api/v1/book`:
Даёт нам все данные о всх Book

Request url:
`Get: /api/v1/book/{id}`:
Даёт нам все данные конкретной Book

Request url:
`Post: /api/v1/book/{id}`:
Добавление Book

Request url:
`Put: /api/v1/book/{id}`:
Изменение данных Book

Request url:
`Delete: /api/v1/book/{id}`:
Удаление орпеделённоой Book


#### Действия с User
Request url:
`Get: /api/v1/user`:
Даёт нам все данные о всх User

Request url:
`Get: /api/v1/user/{id}`:
Даёт нам все данные конкретного User

Request url:
`Post: /api/v1/user/{id}`:
Добавление User

Request url:
`Put: /api/v1/user/{id}`:
Изменение данных User

Request url:
`Delete: /api/v1/user/{id}`:
Удаление орпеделённого User



##(English Version)
#Authorization Server
General Information
The Authorization Server serves as a unified authentication server for all services and modules of the ERP system.

JWT is used for user authentication.

One of the advantages of authentication using JWT is the ability to separate token issuance and user data management into a separate application, delegating the token validation mechanism to client applications. This mechanism is well-suited for a microservices architecture.

We create a few applications: one responsible for token issuance (the authorization server), and another containing business logic. The service with business logic cannot issue tokens but can validate them. Many such applications can exist.

Authentication Mechanism
The client API, typically the front end, sends an object with a login and password.
If the password matches, access and refresh tokens are generated and sent in response.
The client API uses the access token to interact with other API endpoints.
After five minutes, when the access token expires, the front end sends the refresh token and receives a new access token. This cycle continues until the refresh token expires.
The refresh token is valid for 30 days. Around the 25th to 29th day, the client API sends a valid refresh token along with a valid access token and receives a new pair of tokens.
User Roles
ADMIN - Administrator. Has all permissions: USERS_READ, USERS_WRITE.
USER - Regular user. Has permissions for reading users: USERS_READ.
Working with the API
Authorization
Request URL:

POST: /api/auth/login

Request Body:

json
{
    "login": "login",
    "password": "pass"
}
Response Body:

json
{
    "type": "Bearer",
    "accessToken": "qBTmv4oXFFR2GwjexDJ4t6fsIUIUhhXqlktXjXdkcyygs8nPVEwMfo29VDRRepYDVV5IkIxBMzr7OEHXEHd37w==",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4NjU1MTUyOX0.xoZMisD75zKyf_7eC_5v12F9L7wLCWQuLW6gpl5d8xj68FKyjWWm4HYzW5Sc9ls96lqo_3x4W___WX6wtqIdfg"
}
Obtaining a New Access Token
Request URL:

POST: /auth/api/auth/token

Request Body:
json
{
"refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4NDM4OTIxNH0.EkP5_PvTKjU7zav7aHTALcIGXDnS6H7XUbK8NMFHaFjt_613bNyf4INT6760s7669A0RZ1z7uR-vHQtyHbcgtQ"
}
Response Body:

json
{
    "type": "Bearer",
    "accessToken": "qBTmv4oXFFR2GwjexDJ4t6fsIUIUhhXqlktXjXdkcyygs8nPVEwMfo29VDRRepYDVV5IkIxBMzr7OEHXEHd37w==",
    "refreshToken": null
}
Obtaining New Access and Refresh Tokens in Exchange for the Current Refresh Token
Request URL:

POST: /auth/api/auth/refresh

Request Body:

json
{
"refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4NDM4OTIxNH0.EkP5_PvTKjU7zav7aHTALcIGXDnS6H7XUbK8NMFHaFjt_613bNyf4INT6760s7669A0RZ1z7uR-vHQtyHbcgtQ"
}
It's also worth noting that this is a secure method, so you need to include the access token in the Authorization header.

Response Body:

json
{
    "type": "Bearer",
    "accessToken": "qBTmv4oXFFR2GwjexDJ4t6fsIUIUhhXqlktXjXdkcyygs8nPVEwMfo29VDRRepYDVV5IkIxBMzr7OEHXEHd37w==",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4NjU1MzUwNH0.jF8bJCuq6LdM4oJRSuZVW6oRI89NCTrqWziqsV-iABCPPiiKJ9z14tZJN3tJJJ5q2FV0PsHvSnrp2oEmBYWHww"
}
Logging Out a User Session
Request URL:

POST: /auth/api/auth/logout

Request Body:

json
{
"refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4NDM4OTIxNH0.EkP5_PvTKjU7zav7aHTALcIGXDnS6H7XUbK8NMFHaFjt_613bNyf4INT6760s7669A0RZ1z7uR-vHQtyHbcgtQ"
}
Again, it's a secure method, so you need to include the access token in the Authorization header.

LibraryService
General Information
Web services (or web services) are a technology that allows systems to exchange data with each other over a network connection. Web services typically operate over the HTTP protocol or a higher-level protocol. A web service is simply an address or link that, when accessed, allows you to retrieve data or perform an action. The main difference between a web service and other means of data transmission is standardization. By choosing to use web services, you can immediately focus on data structure and available functions.

In this project, I used REST (Representational State Transfer), an architectural style for the interaction of computer systems over a network based on HTTP methods. I also described how the library service works.

Main Entities
Author
Book
LibraryUser

Actions with Author
Request URL:
Get: /api/v1/author:
Gives us all the data about all Authors.

Request URL:
Get: /api/v1/author/{id}:
Gives us all the data about a specific Author.

Request URL:
Post: /api/v1/author/{id}:
Adding an Author.

Request URL:
Put: /api/v1/author/{id}:
Changing Author data.

Request URL:
Delete: /api/v1/author/{id}:
Deleting a specific Author.

Actions with Book
Request URL:
Get: /api/v1/book:
Gives us all the data about all Books.

Request URL:
Get: /api/v1/book/{id}:
Gives us all the data about a specific Book.

Request URL:
Post: /api/v1/book/{id}:
Adding a Book.

Request URL:
Put: /api/v1/book/{id}:
Changing Book data.

Request URL:
Delete: /api/v1/book/{id}:
Deleting a specific Book.

Actions with User
Request URL:
Get: /api/v1/user:
Gives us all the data about all Users.

Request URL:
Get: /api/v1/user/{id}:
Gives us all the data about a specific User.

Request URL:
Post: /api/v1/user/{id}:
Adding a User.

Request URL:
Put: /api/v1/user/{id}:
Changing User data.

Request URL:
Delete: /api/v1/user/{id}:
Deleting a specific User.
