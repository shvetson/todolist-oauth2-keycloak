package ru.shvets.todolist.models.requests.todo

import kotlinx.serialization.Serializable
import ru.shvets.todolist.models.ToDo
import ru.shvets.todolist.models.ToDoId

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  13.05.2023 23:09
 */

@Serializable
data class TodoIdRequest(
    val id: ToDoId,
) {
    constructor(toDo: ToDo) : this(toDo.id)
}