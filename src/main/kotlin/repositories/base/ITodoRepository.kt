package repositories.base

import repositories.todo.TodoResponse
import repositories.todo.TodosResponse
import models.requests.todo.TodoFilterRequest
import models.requests.todo.TodoIdRequest
import models.requests.todo.TodoRequest

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