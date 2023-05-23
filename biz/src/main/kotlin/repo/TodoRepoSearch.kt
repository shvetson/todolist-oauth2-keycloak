package ru.shvets.todolist.biz.repo

import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.models.todo.TodoState
import ru.shvets.todolist.common.repo.todo.DbTodoFilterRequest
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.worker

fun ICorChainDsl<TodoContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск todos в БД по фильтру"
    on { state == TodoState.RUNNING }
    handle {
        val request = DbTodoFilterRequest(
            title = todoFilterValidated.searchString,
            isDone = todoFilterValidated.isDone,
        )
        val result = todoRepo.searchTodos(request)
        val resultTodos = result.data
        if (result.isSuccess && resultTodos != null) {
            todosRepoDone = resultTodos.toMutableList()
        } else {
            state = TodoState.FAILING
            errors.addAll(result.errors)
        }
    }
}