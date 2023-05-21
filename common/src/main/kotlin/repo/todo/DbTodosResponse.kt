package ru.shvets.todolist.common.repo.todo

import kotlinx.serialization.Serializable
import ru.shvets.todolist.common.models.todo.Todo
import ru.shvets.todolist.common.models.todo.TodoError

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  13.05.2023 23:02
 */

@Serializable
data class DbTodosResponse(
    override val data: List<Todo>?,
    override val isSuccess: Boolean,
    override val errors: List<TodoError> = emptyList(),
): IDbResponse<List<Todo>> {

    companion object {
        fun success(result: List<Todo>) = DbTodosResponse(result, true)
        fun error(errors: List<TodoError>) = DbTodosResponse(null, false, errors)
        fun error(error: TodoError) = DbTodosResponse(null, false, listOf(error))
    }
}