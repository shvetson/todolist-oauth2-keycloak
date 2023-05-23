package ru.shvets.todolist.biz.repo

import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.models.todo.TodoState
import ru.shvets.todolist.common.repo.todo.DbTodoRequest
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.worker


fun ICorChainDsl<TodoContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление todo в БД"
    on { state == TodoState.RUNNING }
    handle {
        val request = DbTodoRequest(todoRepoPrepare)
        val result = todoRepo.createTodo(request)
        val resultTodo = result.data
        if (result.isSuccess && resultTodo != null) {
            todoRepoDone = resultTodo
        } else {
            state = TodoState.FAILING
            errors.addAll(result.errors)
        }
    }
}
