package ru.shvets.todolist.common.models.todo

import kotlinx.serialization.Serializable

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
) {

    fun isEmpty() = this == NONE

    companion object {
        val NONE = Todo()
    }
}