package ru.shvets.todolist.biz.repo

import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.model.todo.TodoState
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.worker

fun ICorChainDsl<TodoContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = "Готовим данные к удалению из БД".trimIndent()
    on { state == TodoState.RUNNING }
    handle {
        todoRepoPrepare = todoValidated
    }
}