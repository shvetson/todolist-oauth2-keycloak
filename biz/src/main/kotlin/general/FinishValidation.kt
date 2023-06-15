package ru.shvets.todolist.biz.general

import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.model.todo.TodoState
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.worker


fun ICorChainDsl<TodoContext>.finishTodoValidation(title: String) = worker {
    this.title = title
    on { state == TodoState.RUNNING }
    handle {
        todoValidated = todoValidating
    }
}

fun ICorChainDsl<TodoContext>.finishTodoFilterValidation(title: String) = worker {
    this.title = title
    on { state == TodoState.RUNNING }
    handle {
        todoFilterValidated = todoFilterValidating
    }
}