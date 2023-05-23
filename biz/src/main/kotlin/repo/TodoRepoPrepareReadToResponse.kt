package ru.shvets.todolist.biz.repo

import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.models.todo.TodoState
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.worker

fun ICorChainDsl<TodoContext>.repoPrepareToResponse(title: String) = worker {
    this.title = title
    description = "Подготовка ответа для вывода"
    on { state == TodoState.RUNNING }
    handle { todoRepoDone = todoRepoRead }
}