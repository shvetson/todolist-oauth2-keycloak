package ru.shvets.todolist.biz

import ru.shvets.todolist.biz.general.*
import ru.shvets.todolist.biz.permission.accessValidation
import ru.shvets.todolist.biz.permission.chainPermissions
import ru.shvets.todolist.biz.permission.frontPermissions
import ru.shvets.todolist.biz.permission.searchTypes
import ru.shvets.todolist.biz.repo.*
import ru.shvets.todolist.biz.validation.validateIdNotEmpty
import ru.shvets.todolist.biz.validation.validateIdProperFormat
import ru.shvets.todolist.biz.validation.validateTodo
import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.TodoCorSettings
import ru.shvets.todolist.common.model.todo.TodoCommand
import ru.shvets.todolist.common.model.todo.TodoId
import ru.shvets.todolist.lib.cor.chain
import ru.shvets.todolist.lib.cor.rootChain
import ru.shvets.todolist.lib.cor.worker

class TodoProcessor(private val settings: TodoCorSettings = TodoCorSettings()) {
    suspend fun exec(ctx: TodoContext) = BusinessChain.exec(ctx.apply { settings = this@TodoProcessor.settings })

    companion object {
        private val BusinessChain = rootChain<TodoContext> {
            initStatus("Инициализация статуса")
            initRepo("Инициализация репозитория")

            operation("Создание todo", TodoCommand.CREATE) {
                validation {
                    worker("Копируем поля в todoValidating") { todoValidating = todoRequest.deepCopy() }
                    worker("Очистка id") { todoValidating.id = TodoId.NONE }
                    worker("Очистка заголовка") { todoValidating.title = todoValidating.title.trim() }
                    validateTodo("Проверка todoValidating (не пустой и формат ввода)")
                    finishTodoValidation("Завершение проверок")
                }
                chainPermissions("Вычисление разрешений для пользователя")
                chain {
                    title = "Логика сохранения"
                    accessValidation("Вычисление прав доступа")
                    repoPrepareCreate("Подготовка объекта для сохранения")
                    repoCreate("Создание todo в БД")
                }
                frontPermissions("Вычисление пользовательских разрешений для фронтенда")
                prepareResult("Подготовка ответа")
            }
            operation("Получить todo", TodoCommand.READ) {
                validation {
                    worker("Копируем поля в todoValidating") { todoValidating = todoRequest.deepCopy() }
                    worker("Очистка id") { todoValidating.id = TodoId(todoValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    finishTodoValidation("Успешное завершение процедуры валидации")
                }
                chainPermissions("Проверка разрешений для пользователя")
                chain {
                    title = "Логика чтения"
                    repoRead("Чтение todo из БД")
                    accessValidation("Вычисление прав доступа")
                    repoPrepareToResponse("Подготовка ответа для Read")
                }
                frontPermissions("Вычисление пользовательских разрешений для фронтенда")
                prepareResult("Подготовка ответа")
            }
            operation("Изменить todo", TodoCommand.UPDATE) {
                validation {
                    worker("Копируем поля в todoValidating") { todoValidating = todoRequest.deepCopy() }
                    worker("Очистка id") { todoValidating.id = TodoId(todoValidating.id.asString().trim()) }
                    worker("Очистка заголовка") { todoValidating.title = todoValidating.title.trim() }
                    validateTodo("Проверка todoValidating (не пустой и формат ввода)")
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    finishTodoValidation("Успешное завершение процедуры валидации")
                }
                chainPermissions("Вычисление разрешений для пользователя")
                chain {
                    title = "Логика сохранения"
                    repoRead("Чтение todo из БД")
                    accessValidation("Вычисление прав доступа")
                    repoPrepareUpdate("Подготовка объекта для обновления")
                    repoUpdate("Обновление todo в БД")
                }
                frontPermissions("Вычисление пользовательских разрешений для фронтенда")
                prepareResult("Подготовка ответа")
            }
            operation("Удалить todo", TodoCommand.DELETE) {
                validation {
                    worker("Копируем поля в todoValidating") { todoValidating = todoRequest.deepCopy() }
                    worker("Очистка id") { todoValidating.id = TodoId(todoValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    finishTodoValidation("Успешное завершение процедуры валидации")
                }
                chainPermissions("Вычисление разрешений для пользователя")
                chain {
                    title = "Логика удаления"
                    repoRead("Чтение todo из БД")
                    accessValidation("Вычисление прав доступа")
                    repoPrepareDelete("Подготовка объекта для удаления")
                    repoDelete("Удаление todo из БД")
                }
                frontPermissions("Вычисление пользовательских разрешений для фронтенда")
                prepareResult("Подготовка ответа")
            }
            operation("Поиск todos", TodoCommand.SEARCH) {
                validation {
                    worker("Копируем поля в todoFilterValidating") { todoFilterValidating = todoFilterRequest }
                    finishTodoFilterValidation("Успешное завершение процедуры валидации")
                }
                chainPermissions("Вычисление разрешений для пользователя")
                searchTypes("Подготовка поискового запроса")
                repoSearch("Поиск todos в БД по фильтру")
                frontPermissions("Вычисление пользовательских разрешений для фронтенда")
                prepareResult("Подготовка ответа")
            }
        }.build()
    }
}