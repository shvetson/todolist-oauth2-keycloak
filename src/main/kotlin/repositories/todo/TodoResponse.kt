package repositories.todo

import kotlinx.serialization.Serializable
import models.ToDo
import repositories.base.BaseError
import repositories.base.IBaseResponse
import helpers.errorNotFound as tdErrorNotFound
import helpers.errorSave as tdErrorSave
import helpers.errorEmptyId as tdErrorEmptyId

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
        fun success(result: ToDo, isSuccess: Boolean = true) = TodoResponse(result, isSuccess)
        fun error(errors: List<BaseError>) = TodoResponse(null, false, errors)
        private fun error(error: BaseError) = TodoResponse(null, false, listOf(error))

        val errorNotFound = error(tdErrorNotFound)
        val errorSave = error(tdErrorSave)
        val errorEmptyId = error(tdErrorEmptyId)
    }
}