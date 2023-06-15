package ru.shvets.todolist.biz.validation

import io.konform.validation.Invalid
import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.helper.*
import ru.shvets.todolist.common.model.todo.Todo
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.worker

fun ICorChainDsl<TodoContext>.validateTodo(title: String) = worker {
    this.title = title
    on { todoValidating.validate() is Invalid<Todo> }
    handle {
        val validationResult = todoValidating.validate()
        val listErrors = errorValidation(validationResult)
        fails(listErrors)
    }
}