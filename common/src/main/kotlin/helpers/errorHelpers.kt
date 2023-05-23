package ru.shvets.todolist.common.helpers

import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.models.todo.TodoError
import ru.shvets.todolist.common.models.todo.TodoState

fun Throwable.asTodoError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = TodoError(
    code = code,
    group = group,
    field = "",
    description = message,
)

fun TodoContext.addError(vararg error: TodoError) = errors.addAll(error)

fun TodoContext.fail(error: TodoError) {
    addError(error)
    state = TodoState.FAILING
}

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
) = TodoError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    description = "validation error for field $field: $description",
)

fun errorAdministration(
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    field: String = "",
    violationCode: String,
    description: String,
    exception: Exception? = null,
) = TodoError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    description = "microservice management error: $description,  $exception",
)

//val errorEmptyId = BaseError(
//    code = "id-empty",
//    group = "cruds",
//    field = "id",
//    description = "id must not be null or blank"
//)

val errorEmptyId = error {
    code = "id-empty"
    group = "cruds"
    field = "id"
    description = "id must not be null or blank"
}

//val errorNotFound = BaseError(
//    code = "not-found",
//    group = "cruds",
//    field = "id",
//    description = "not found",
//)

val errorNotFound = error {
    code = "not-found"
    group = "cruds"
    field = "id"
    description = "not found"
}

//val errorSave = BaseError(
//    code = "not-save",
//    group = "cruds",
//    field = "row",
//    description = "not save a new item",
//)

val errorSave = error {
    code = "not-save"
    group = "cruds"
    field = "row"
    description = "not save a new item"
}

//fun err(block: (BaseError) -> Unit): BaseError {
//    val er = BaseError()
//    block(er)
//    return er
//}

fun error(block: TodoError.()-> Unit): TodoError {
    val error = TodoError()
    error.block()
    return error
}