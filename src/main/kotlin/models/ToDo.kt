package ru.shvets.todolist.models

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  09.05.2023 18:59
 */

@Serializable
data class ToDo(
    val id: Long = 0L,
    var title: String,
    var isDone: Boolean
)