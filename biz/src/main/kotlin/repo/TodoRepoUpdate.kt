package ru.shvets.todolist.biz.repo

import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.model.todo.TodoState
import ru.shvets.todolist.common.repo.todo.DbTodoRequest
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.worker

fun ICorChainDsl<TodoContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == TodoState.RUNNING }
    handle {
        val request = DbTodoRequest(todoRepoPrepare)
        val result = todoRepo.updateTodo(request)
        val resultTodo = result.data
        if (result.isSuccess && resultTodo != null) {
            todoRepoDone = resultTodo
        } else {
            state = TodoState.FAILING
            errors.addAll(result.errors)
            todoRepoDone
        }
    }
}