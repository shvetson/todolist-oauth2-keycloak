package ru.shvets.todolist.common.model.todo

import io.konform.validation.Validation
import io.konform.validation.ValidationResult
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import ru.shvets.todolist.common.helper.NONE
import ru.shvets.todolist.common.permission.TodoPermission
import ru.shvets.todolist.common.permission.TodoPrincipalRelations
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
    var created: Instant = Instant.NONE,
    var updated: Instant = Instant.NONE,
    var ownerId: TodoUserId = TodoUserId.NONE,
    var principalRelations: Set<TodoPrincipalRelations> = emptySet(),
    val permissionsClient: MutableSet<TodoPermission> = mutableSetOf(),
) : Validatable<Todo> {

    override fun validate(): ValidationResult<Todo> = Validation<Todo> {
        val regExp = Regex("^[a-zA-Z]+\$")

        Todo::title required {
            minLength(5)
            maxLength(50)
        }
        Todo::title required {
//            pattern(regExp) hint "field must contain letters"
        }
        Todo::isDone required {}
    }(this)

    fun deepCopy(): Todo = copy(
        principalRelations = principalRelations.toSet(),
        permissionsClient = permissionsClient.toMutableSet(),
    )

    fun isEmpty() = this == NONE

    companion object {
        val NONE = Todo()
    }
}