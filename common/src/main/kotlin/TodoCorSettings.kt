package ru.shvets.todolist.common

import ru.shvets.todolist.common.repo.todo.ITodoRepository

data class TodoCorSettings(
    val repo: ITodoRepository = ITodoRepository.NONE,
) {
    companion object {
        val NONE = TodoCorSettings()
    }
}
