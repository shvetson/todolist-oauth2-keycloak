package ru.shvets.todolist.repositories

import ru.shvets.todolist.models.ToDo
import ru.shvets.todolist.models.ToDoDto

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  09.05.2023 19:17
 */

interface ToDoRepository {
    suspend fun getAllToDo(): List<ToDo>?
    suspend fun getToDo(id: Long): ToDo?

    suspend fun addToDo(draft: ToDoDto): ToDo?
    suspend fun removeTodo(id: Long): Boolean
    suspend fun updateToDo(id: Long, draft: ToDoDto): Boolean
}