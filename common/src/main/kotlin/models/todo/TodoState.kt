package ru.shvets.todolist.common.models.todo

import kotlinx.serialization.Serializable

@Serializable
enum class TodoState {
    NONE,
    RUNNING,
    FAILING,
    FINISHING,
}
