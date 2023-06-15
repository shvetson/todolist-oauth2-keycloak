package ru.shvets.todolist.common.validate

import io.konform.validation.Invalid
import ru.shvets.todolist.common.model.todo.TodoError
import ru.shvets.todolist.common.repo.todo.DbTodoResponse

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  12.05.2023 20:54
 */

fun resultErrorValidation(validation: Invalid<out Any>): DbTodoResponse {
    val listErrors = mutableListOf<TodoError>()

    listOf(validation).flatMap { it.errors }.forEach {
        val field = if (it.dataPath.startsWith(".")) it.dataPath.drop(1) else it.dataPath
        val message = it.message
        val error = TodoError(
            code = "error input",
            group = "validation",
            field = field,
            description = message
        )
        listErrors.add(error)
    }

    return DbTodoResponse(
        data = null,
        isSuccess = false,
        errors = listErrors
    )
}

val resultErrorSaving = DbTodoResponse(
    data = null,
    isSuccess = false,
    errors = listOf(
        TodoError(
            code = "error saving",
            group = "cruds",
            field = "",
            description = "error saving a new object"
        )
    )
)

val resultErrorEmptyId = DbTodoResponse(
    data = null,
    isSuccess = false,
    errors = listOf(
        TodoError(
            code = "error input",
            group = "validation",
            field = "id",
            description = "id must not be null or blank"
        )
    )
)