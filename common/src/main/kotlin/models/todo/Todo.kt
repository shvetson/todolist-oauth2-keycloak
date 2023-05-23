package ru.shvets.todolist.common.models.todo

import io.konform.validation.Validation
import io.konform.validation.ValidationResult
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.pattern
import kotlinx.serialization.Serializable
import ru.shvets.todolist.common.validate.Validatable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  09.05.2023 18:59
 */

@Serializable
data class Todo(
    var id: TodoId = TodoId.NONE,
    var title: String = "",
    var isDone: Boolean = false,
) : Validatable<Todo> {

    override fun validate(): ValidationResult<Todo> = Validation<Todo> {
        val regExp = Regex("^[a-zA-Z]+\$")

        Todo::title required {
            minLength(5)
            maxLength(50)
        }
        Todo::title required {
            pattern(regExp) hint "field must contain letters"
        }
        Todo::isDone required {}
    }(this)

    fun isEmpty() = this == NONE

    companion object {
        val NONE = Todo()
    }
}