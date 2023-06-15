package ru.shvets.todolist.common.model.todo

import kotlinx.serialization.Serializable

@Serializable
enum class TodoState {
    NONE,
    RUNNING,
    FAILING,
    FINISHING,
}