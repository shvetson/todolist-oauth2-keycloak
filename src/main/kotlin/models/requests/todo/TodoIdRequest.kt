package models.requests.todo

import kotlinx.serialization.Serializable
import models.ToDo
import models.ToDoId

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