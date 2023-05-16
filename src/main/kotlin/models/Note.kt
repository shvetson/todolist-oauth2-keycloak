package ru.shvets.todolist.models

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  12.05.2023 19:26
 */

data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val todoId: Long,
)