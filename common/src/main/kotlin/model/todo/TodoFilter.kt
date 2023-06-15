package ru.shvets.todolist.common.model.todo

import kotlinx.serialization.Serializable

@Serializable
data class TodoFilter(
    var searchString: String = "",
    var isDone: Boolean? = null,
    var ownerId: TodoUserId = TodoUserId.NONE,
    var searchPermissions: MutableSet<TodoSearchPermissions> = mutableSetOf(),
)