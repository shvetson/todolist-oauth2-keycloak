package ru.shvets.todolist.biz.general

import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.helper.errorAdministration
import ru.shvets.todolist.common.helper.fail
import ru.shvets.todolist.common.repo.todo.ITodoRepository
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.worker

fun ICorChainDsl<TodoContext>.initRepo(title: String) = worker {
    this.title = title
    description = "Определение репозитория"
    handle {
        todoRepo = settings.repo
        if (todoRepo == ITodoRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured. Please, contact the administrator staff"
            )
        )
    }
}