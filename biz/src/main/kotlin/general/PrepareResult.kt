package ru.shvets.todolist.biz.general

import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.model.todo.TodoState
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.worker

fun ICorChainDsl<TodoContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    handle {
        todoResponse = todoRepoDone
        todosResponse = todosRepoDone
        state = when (val st = state) {
            TodoState.RUNNING -> TodoState.FINISHING
            else -> st
        }
    }
}