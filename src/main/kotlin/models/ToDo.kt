package ru.shvets.todolist.entities

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  09.05.2023 18:59
 */

data class ToDo(
    val id: Long,
    val title: String,
    val isDone: Boolean
)