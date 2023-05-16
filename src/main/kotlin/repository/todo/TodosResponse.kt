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
data class TodosResponse(
    override val data: List<ToDo>?,
    override val isSuccess: Boolean,
    override val errors: List<BaseError> = emptyList(),
): IBaseResponse<List<ToDo>> {

    companion object {
        fun success(result: List<ToDo>) = TodosResponse(result, true)
        fun error(errors: List<BaseError>) = TodosResponse(null, false, errors)
        fun error(error: BaseError) = TodosResponse(null, false, listOf(error))
    }
}