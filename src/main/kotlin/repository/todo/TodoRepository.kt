package ru.shvets.todolist.repository.todo

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import ru.shvets.todolist.database.DatabaseFactory.dbQuery
import ru.shvets.todolist.entities.ToDos
import ru.shvets.todolist.models.ToDo
import ru.shvets.todolist.models.ToDoId
import ru.shvets.todolist.repository.base.ITodoRepository

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  15.05.2023 11:43
 */

class TodoRepository() : ITodoRepository {
    override suspend fun createTodo(request: TodoRequest): TodoResponse = dbQuery {
        val result = ToDos.insert { td ->
            td[ToDos.title] = request.toDo.title
            td[ToDos.isDone] = request.toDo.isDone
        }.resultedValues?.singleOrNull()?.let(::rowToToDo)

        TodoResponse(
            data = result,
            isSuccess = true
        )
    }

    override suspend fun readTodo(request: TodoIdRequest): TodoResponse = dbQuery {
        val id = request.id
        val result = ToDos.select { ToDos.id eq (id.asString().toLong()) }
            .map(::rowToToDo)
            .singleOrNull()

        TodoResponse(
            data = result,
            isSuccess = true
        )
    }

    override suspend fun updateTodo(request: TodoRequest): TodoResponse = dbQuery {
        val id = request.toDo.id

        val result: Boolean = ToDos.update({ ToDos.id eq (id.asString().toLong()) }) { td ->
            td[ToDos.title] = request.toDo.title
            td[ToDos.isDone] = request.toDo.isDone
        } > 0

        TodoResponse(
            data = request.toDo,
            isSuccess = result
        )
    }

    override suspend fun deleteTodo(request: TodoIdRequest): TodoResponse = dbQuery {
        val id = request.id
        val todo = runBlocking { readTodo(request).data }

        val result: Boolean = ToDos.deleteWhere { ToDos.id eq (id.asString().toLong()) } > 0

        TodoResponse(
            data = todo,
            isSuccess = result
        )
    }

    override suspend fun searchTodos(request: TodoFilterRequest): TodosResponse = dbQuery {
        val title = request.title

        val result = ToDos.select { ToDos.title like "%${title}%" }
            .orderBy(ToDos.id)
            .map(::rowToToDo)

        TodosResponse(
            data = result,
            isSuccess = true
        )
    }

    private fun rowToToDo(row: ResultRow): ToDo =
        ToDo(
            id = ToDoId(row[ToDos.id].toString()),
            title = row[ToDos.title],
            isDone = row[ToDos.isDone]
        )
}