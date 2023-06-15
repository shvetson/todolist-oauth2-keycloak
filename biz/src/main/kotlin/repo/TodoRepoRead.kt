package ru.shvets.todolist.biz.repo

import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.model.todo.TodoState
import ru.shvets.todolist.common.repo.todo.DbTodoIdRequest
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.worker

fun ICorChainDsl<TodoContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение todo из БД"
    on { state == TodoState.RUNNING }
    handle {
        val request = DbTodoIdRequest(todoValidated)
        val result = todoRepo.readTodo(request)
        val resultTodo = result.data
        if (result.isSuccess && resultTodo != null) {
            todoRepoRead = resultTodo
        } else {
            state = TodoState.FAILING
            errors.addAll(result.errors)
        }
    }
}