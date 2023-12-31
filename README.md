# Todo list 2023

Простой список дел, задач, напоминаний и тд.
- Backend, rest api.
- Стэк - kotlin, ktor, postgresql, openApi, konform.

# Структура проекта
## Транспортные модели, API
1. [specs](specs) - Описание API в форме OpenAPI-спецификаций
2. [api](api) - Генерация первой версии транспортных моделей с Jackson
3. [common](common) - Модуль с общими классами для модулей проекта. В частности, там располагаются внутренние модели и контекст.
4. [mapper](mapper) - Маппер между внутренними моделями и моделями API

## Фреймворки и транспорт
1. [app](app) - Приложение на Ktor JVM

## Модули бизнес-логики
1. [lib-cor](lib-cor) - Библиотека цепочки обязанностей для бизнес-логики
2. [biz](biz) - Модуль бизнес-логики приложения (подготовка, валидация, проверка прав и выполнение cruds операции)

## Мониторинг и логирование
1. [deploy](deploy) - Инструменты мониторинга и деплоя

## Хранение, репозитории, базы данных
1. [repo-psql](repo-psql) - Репозиторий на базе PostgreSQL

## Аутентификация (oauth2)
1. [auth](auth) - Аутентификация и авторизация