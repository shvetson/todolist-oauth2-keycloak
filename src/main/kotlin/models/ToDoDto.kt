package ru.shvets.todolist.models

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  09.05.2023 20:02
 */

@Serializable
data class ToDoDto(
    val title: String,
    val isDone: Boolean,
)
