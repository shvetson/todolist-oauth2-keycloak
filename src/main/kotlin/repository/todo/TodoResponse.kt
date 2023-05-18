package ru.shvets.todolist.repository.todo

import kotlinx.serialization.Serializable
import ru.shvets.todolist.models.ToDo
import ru.shvets.todolist.repository.base.BaseError
import ru.shvets.todolist.repository.base.IBaseResponse
import ru.shvets.todolist.helper.errorNotFound as tdErrorNotFound
import ru.shvets.todolist.helper.errorSave as tdErrorSave
import ru.shvets.todolist.helper.errorEmptyId as tdErrorEmptyId

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  13.05.2023 23:02
 */

@Serializable
data class TodoResponse(
    override val data: ToDo?,
    override val isSuccess: Boolean,
    override val errors: List<BaseError> = emptyList(),
): IBaseResponse<ToDo> {

    companion object {
        fun success(result: ToDo) = TodoResponse(result, true)
        fun result(result: ToDo, isSuccess: Boolean) = TodoResponse(result, isSuccess)
        fun error(errors: List<BaseError>) = TodoResponse(null, false, errors)
        private fun error(error: BaseError) = TodoResponse(null, false, listOf(error))

        val errorNotFound = error(tdErrorNotFound)
        val errorSave = error(tdErrorSave)
        val errorEmptyId = error(tdErrorEmptyId)
    }
}