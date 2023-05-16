package ru.shvets.todolist.repository.todo

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  13.05.2023 23:19
 */

@Serializable
data class TodoFilterRequest(
    val title: String = "",
)