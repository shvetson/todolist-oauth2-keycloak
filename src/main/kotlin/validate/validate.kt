package ru.shvets.todolist.validate

import io.konform.validation.Invalid
import ru.shvets.todolist.repository.base.BaseError
import ru.shvets.todolist.repository.todo.TodoResponse

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  12.05.2023 20:54
 */

fun resultErrorValidation(validation: Invalid<out Any>): TodoResponse {
    val listErrors = mutableListOf<BaseError>()

    listOf(validation).flatMap { it.errors }.forEach {
        val field = if (it.dataPath.startsWith(".")) it.dataPath.drop(1) else it.dataPath
        val message = it.message
        val error = BaseError(
            code = "error input",
            group = "validation",
            field = field,
            description = message
        )
        listErrors.add(error)
    }

    return TodoResponse(
        data = null,
        isSuccess = false,
        errors = listErrors
    )
}

val resultErrorSaving = TodoResponse(
    data = null,
    isSuccess = false,
    errors = listOf(
        BaseError(
            code = "error saving",
            group = "cruds",
            field = "",
            description = "error saving a new object"
        )
    )
)

val resultErrorEmptyId = TodoResponse(
    data = null,
    isSuccess = false,
    errors = listOf(
        BaseError(
            code = "error input",
            group = "validation",
            field = "id",
            description = "id must not be null or blank"
        )
    )
)