package ru.shvets.todolist.models.requests.todo

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  13.05.2023 23:19
 */

@Serializable
data class TodoFilterRequest(
    val title: String = "",
    val isDone:Boolean? = null,
)