package ru.shvets.todolist.common.repo.todo

import io.konform.validation.Validation
import io.konform.validation.ValidationResult
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import kotlinx.serialization.Serializable
import ru.shvets.todolist.common.model.todo.Todo
import ru.shvets.todolist.common.validate.Validatable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  13.05.2023 23:14
 */

@Serializable
data class DbTodoRequest(
    val toDo: Todo,
) : Validatable<DbTodoRequest> {

    override fun validate(): ValidationResult<DbTodoRequest> = Validation<DbTodoRequest> {
        DbTodoRequest::toDo {
            Todo::title required {
                minLength(5)
                maxLength(50)
            }
            Todo::isDone required {}
        }
    }(this)
}