package ru.shvets.todolist.repository.base

import ru.shvets.todolist.models.requests.todo.TodoFilterRequest
import ru.shvets.todolist.models.requests.todo.TodoIdRequest
import ru.shvets.todolist.models.requests.todo.TodoRequest
import ru.shvets.todolist.repository.todo.*

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  13.05.2023 22:57
 */

interface ITodoRepository {
    suspend fun createTodo(request: TodoRequest): TodoResponse
    suspend fun readTodo(request: TodoIdRequest): TodoResponse
    suspend fun updateTodo(request: TodoRequest): TodoResponse
    suspend fun deleteTodo(request: TodoIdRequest): TodoResponse
    suspend fun searchTodos(request: TodoFilterRequest): TodosResponse
}