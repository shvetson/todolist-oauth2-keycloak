package ru.shvets.todolist.common.models.todo

import kotlinx.serialization.Serializable

@Serializable
data class TodoError(
    var code: String = "",
    var group: String = "",
    var field: String = "",
    var description: String = "",
)