package ru.shvets.todolist.common.permission

import ru.shvets.todolist.common.model.todo.TodoUserId

data class TodoPrincipalModel(
    val id: TodoUserId = TodoUserId.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
    val groups: Set<TodoUserGroups> = emptySet()
) {
    companion object {
        val NONE = TodoPrincipalModel()
    }
}