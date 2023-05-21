package ru.shvets.todolist.common.repo.todo

import kotlinx.serialization.Serializable
import ru.shvets.todolist.common.models.todo.Todo
import ru.shvets.todolist.common.models.todo.TodoId

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  13.05.2023 23:09
 */

@Serializable
data class DbTodoIdRequest(
    val id: TodoId,
) {
    constructor(toDo: Todo) : this(toDo.id)
}