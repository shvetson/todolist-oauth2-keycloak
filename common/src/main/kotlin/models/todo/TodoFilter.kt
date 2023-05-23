package ru.shvets.todolist.common.models.todo

import kotlinx.serialization.Serializable

@Serializable
data class TodoFilter(
    var searchString: String = "",
    var isDone: Boolean? = null,
)