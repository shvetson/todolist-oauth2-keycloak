package ru.shvets.todolist.common.model.todo

import kotlinx.serialization.Serializable

@Serializable
enum class TodoCommand {
    NONE,
    CREATE,
    READ,
    UPDATE,
    DELETE,
    SEARCH,
}