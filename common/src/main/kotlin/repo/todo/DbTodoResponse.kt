package ru.shvets.todolist.common.repo.todo

import kotlinx.serialization.Serializable
import ru.shvets.todolist.common.model.todo.Todo
import ru.shvets.todolist.common.model.todo.TodoError
import ru.shvets.todolist.common.helper.errorEmptyId as tdErrorEmptyId
import ru.shvets.todolist.common.helper.errorSave as tdErrorSave
import ru.shvets.todolist.common.helper.errorNotFound as tdErrorNotFound

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  13.05.2023 23:02
 */

@Serializable
data class DbTodoResponse(
    override val data: Todo?,
    override val isSuccess: Boolean,
    override val errors: List<TodoError> = emptyList(),
): IDbResponse<Todo> {

    companion object {
        fun success(result: Todo, isSuccess: Boolean = true) = DbTodoResponse(result, isSuccess)
        fun error(errors: List<TodoError>) = DbTodoResponse(null, false, errors)
        private fun error(error: TodoError) = DbTodoResponse(null, false, listOf(error))

        val errorNotFound = error(tdErrorNotFound)
        val errorSave = error(tdErrorSave)
        val errorEmptyId = error(tdErrorEmptyId)
    }
}