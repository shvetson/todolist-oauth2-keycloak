package ru.shvets.todolist.models.requests

import io.konform.validation.Validation
import io.konform.validation.ValidationResult
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import kotlinx.serialization.Serializable
import ru.shvets.todolist.validate.Validatable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  09.05.2023 20:02
 */

@Serializable
data class ToDoRequest(
    val title: String,
    val isDone: Boolean,
): Validatable<ToDoRequest> {

    override fun validate(): ValidationResult<ToDoRequest> = Validation<ToDoRequest> {
        ToDoRequest::title required {
            minLength(5)
            maxLength(50)
        }
        ToDoRequest::isDone required {}
    }(this)
}