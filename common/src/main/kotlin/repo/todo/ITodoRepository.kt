package ru.shvets.todolist.common.repo.todo

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  13.05.2023 22:57
 */

interface ITodoRepository {
    suspend fun createTodo(request: DbTodoRequest): DbTodoResponse
    suspend fun readTodo(request: DbTodoIdRequest): DbTodoResponse
    suspend fun updateTodo(request: DbTodoRequest): DbTodoResponse
    suspend fun deleteTodo(request: DbTodoIdRequest): DbTodoResponse
    suspend fun searchTodos(request: DbTodoFilterRequest): DbTodosResponse

    companion object {
        val NONE = object : ITodoRepository {
            override suspend fun createTodo(request: DbTodoRequest): DbTodoResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readTodo(request: DbTodoIdRequest): DbTodoResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateTodo(request: DbTodoRequest): DbTodoResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteTodo(request: DbTodoIdRequest): DbTodoResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchTodos(request: DbTodoFilterRequest): DbTodosResponse {
                TODO("Not yet implemented")
            }

        }
    }
}