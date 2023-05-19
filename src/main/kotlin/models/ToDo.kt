package models

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  09.05.2023 18:59
 */

@Serializable
data class ToDo(
    var id: ToDoId = ToDoId.NONE,
    var title: String = "",
    var isDone: Boolean = false,
)