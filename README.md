# Test task. Currency conversion.

Тестовое задание.

С использованием фреймворка Spring необходимо разработать сервис для конвертации валют и сбора статистики операций.

API: /exchange, /stats

/exchange

Запрос: id пользователя, сумма в исходной валюте, исходная валюта, целевая валюта. Ответ: id запроса, сумма в целевой валюте. Можно использовать внешние api для конвертации или для получения курса конвертации.

/stats

Предоставление доступа к выборочной информации по запросам.

Примеры запросов:

Пользователи, запросившие конвертацию больше 10 000 $ за один запрос.

Пользователи, суммарный запрошенный объём которых больше 100 000 $.

Рейтинг направлений конвертации валют по популярности.

Для решения задания были использованы: Spring Boot, Thymeleaf, JPA, Security, Openfeign, h2, Lombok, Liquibase. Реализована авторизация, регистрация нового пользователя, конвертация валют с помощью https://api.coingate.com/v2/rates/merchant, просмотр статистики только для пользователя с правами модератора.
