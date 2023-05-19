package models.requests.todo

import io.konform.validation.Validation
import io.konform.validation.ValidationResult
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import kotlinx.serialization.Serializable
import models.ToDo
import validate.Validatable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  13.05.2023 23:14
 */

@Serializable
data class TodoRequest(
    val toDo: ToDo,
) : Validatable<TodoRequest> {

    override fun validate(): ValidationResult<TodoRequest> = Validation<TodoRequest> {
        TodoRequest::toDo {
            ToDo::title required {
                minLength(5)
                maxLength(50)
            }
            ToDo::isDone required {}
        }
    }(this)
}