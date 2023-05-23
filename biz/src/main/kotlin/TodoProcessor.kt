package ru.shvets.todolist.biz

import ru.shvets.todolist.biz.general.*
import ru.shvets.todolist.biz.repo.*
import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.TodoCorSettings
import ru.shvets.todolist.common.models.todo.TodoCommand
import ru.shvets.todolist.common.models.todo.TodoId
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
                    worker("Копируем поля в todoValidating") { todoValidating = todoRequest }
                    worker("Очистка id") { todoValidating.id = TodoId.NONE }
                    worker("Очистка заголовка") { todoValidating.title = todoValidating.title.trim() }

//                    validateTitleNotEmpty("Проверка, что заголовок не пуст")
//                    validateTitleHasContent("Проверка символов")
//                    validateDescriptionNotEmpty("Проверка, что описание не пусто")
//                    validateDescriptionHasContent("Проверка символов")

                    finishAdValidation("Завершение проверок")
                }
                chain {
                    title = "Логика сохранения"
                    repoPrepareCreate("Подготовка объекта для сохранения")
                    repoCreate("Создание todo в БД")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Получить todo", TodoCommand.READ) {
                validation {
                    worker("Копируем поля в todoValidating") { todoValidating = todoRequest }
                    worker("Очистка id") { todoValidating.id = TodoId(todoValidating.id.asString().trim()) }

//                    validateIdNotEmpty("Проверка на непустой id")
//                    validateIdProperFormat("Проверка формата id")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика чтения"
                    repoRead("Чтение объявления из БД")
                    repoPrepareToResponse("Подготовка ответа для Read")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Изменить todo", TodoCommand.UPDATE) {
                validation {
                    worker("Копируем поля в todoValidating") { todoValidating = todoRequest }
                    worker("Очистка id") { todoValidating.id = TodoId(todoValidating.id.asString().trim()) }
                    worker("Очистка заголовка") { todoValidating.title = todoValidating.title.trim() }

//                    validateIdNotEmpty("Проверка на непустой id")
//                    validateIdProperFormat("Проверка формата id")
//                    validateLockNotEmpty("Проверка на непустой lock")
//                    validateLockProperFormat("Проверка формата lock")
//                    validateTitleNotEmpty("Проверка на непустой заголовок")
//                    validateTitleHasContent("Проверка на наличие содержания в заголовке")
//                    validateDescriptionNotEmpty("Проверка на непустое описание")
//                    validateDescriptionHasContent("Проверка на наличие содержания в описании")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика сохранения"
                    repoRead("Чтение todo из БД")
                    repoPrepareUpdate("Подготовка объекта для обновления")
                    repoUpdate("Обновление todo в БД")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Удалить todo", TodoCommand.DELETE) {
                validation {
                    worker("Копируем поля в todoValidating") { todoValidating = todoRequest }
                    worker("Очистка id") { todoValidating.id = TodoId(todoValidating.id.asString().trim()) }

//                    validateIdNotEmpty("Проверка на непустой id")
//                    validateIdProperFormat("Проверка формата id")
//                    validateLockNotEmpty("Проверка на непустой lock")
//                    validateLockProperFormat("Проверка формата lock")
                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика удаления"
                    repoRead("Чтение todo из БД")
                    repoPrepareDelete("Подготовка объекта для удаления")
                    repoDelete("Удаление todo из БД")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Поиск todos", TodoCommand.SEARCH) {
                validation {
                    worker("Копируем поля в todoFilterValidating") { todoFilterValidating = todoFilterRequest }
                    finishAdFilterValidation("Успешное завершение процедуры валидации")
                }
                repoSearch("Поиск todos в БД по фильтру")
                prepareResult("Подготовка ответа")
            }
        }.build()
    }
}