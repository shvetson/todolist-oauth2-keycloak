package ru.shvets.todolist.biz.repo

import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.model.todo.TodoState
import ru.shvets.todolist.common.repo.todo.DbTodoIdRequest
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.worker

fun ICorChainDsl<TodoContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление объявления из БД по ID"
    on { state == TodoState.RUNNING }
    handle {
        val request = DbTodoIdRequest(todoRepoPrepare)
        val result = todoRepo.deleteTodo(request)
        if (!result.isSuccess) {
            state = TodoState.FAILING
            errors.addAll(result.errors)
        }
        todoRepoDone = todoRepoRead.deepCopy()
    }
}