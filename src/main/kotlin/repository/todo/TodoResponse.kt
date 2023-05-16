package ru.shvets.todolist.repository.todo

import kotlinx.serialization.Serializable
import ru.shvets.todolist.models.ToDo
import ru.shvets.todolist.repository.base.BaseError
import ru.shvets.todolist.repository.base.IBaseResponse

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
        fun error(errors: List<BaseError>) = TodoResponse(null, false, errors)
        fun error(error: BaseError) = TodoResponse(null, false, listOf(error))
    }
}