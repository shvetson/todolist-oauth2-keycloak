package ru.shvets.todolist.biz.general

import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.models.todo.TodoState
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.worker

fun ICorChainDsl<TodoContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == TodoState.NONE }
    handle { state = TodoState.RUNNING }
}