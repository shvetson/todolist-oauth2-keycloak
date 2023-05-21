package ru.shvets.todolist.app.repositories

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import ru.shvets.todolist.app.database.DatabaseFactory.dbQuery
import ru.shvets.todolist.app.entities.TodosTable
import ru.shvets.todolist.app.entities.TodosTable.fromRow
import ru.shvets.todolist.common.repo.todo.DbTodoFilterRequest
import ru.shvets.todolist.common.repo.todo.DbTodoIdRequest
import ru.shvets.todolist.common.repo.todo.DbTodoRequest
import ru.shvets.todolist.common.repo.todo.DbTodoResponse
import ru.shvets.todolist.common.repo.todo.DbTodosResponse
import ru.shvets.todolist.common.repo.todo.ITodoRepository

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  15.05.2023 11:43
 */

class TodoRepository() : ITodoRepository {
    override suspend fun createTodo(request: DbTodoRequest): DbTodoResponse = dbQuery {
        val toDo = request.toDo
        val res = TodosTable.insert { toRow(it, toDo) }
            .resultedValues?.singleOrNull()?.let(::fromRow) ?: return@dbQuery DbTodoResponse.errorSave
        DbTodoResponse.success(res)
    }

    override suspend fun readTodo(request: DbTodoIdRequest): DbTodoResponse = dbQuery {
        val id = request.id
        val res = TodosTable.select { TodosTable.id eq (id.asString().toLong()) }
            .map(::fromRow)
            .singleOrNull() ?: return@dbQuery DbTodoResponse.errorNotFound
        DbTodoResponse.success(res)
    }

    override suspend fun updateTodo(request: DbTodoRequest): DbTodoResponse = dbQuery {
        val id = request.toDo.id
//        if (id == ToDoId.NONE) return@dbQuery TodoResponse.errorEmptyId
        val todo = runBlocking { readTodo(DbTodoIdRequest(id)).data } ?: return@dbQuery DbTodoResponse.errorNotFound
        val toDo = request.toDo
        val res: Boolean = TodosTable.update({ TodosTable.id eq (id.asString().toLong()) }) { toRow(it, toDo) } > 0
        DbTodoResponse.success(toDo, res)
    }

    override suspend fun deleteTodo(request: DbTodoIdRequest): DbTodoResponse = dbQuery {
        val id = request.id
        val todo = runBlocking { readTodo(request).data } ?: return@dbQuery DbTodoResponse.errorNotFound
        val res: Boolean = TodosTable.deleteWhere { TodosTable.id eq (id.asString().toLong()) } > 0
        DbTodoResponse.success(todo, res)
    }

    override suspend fun searchTodos(request: DbTodoFilterRequest): DbTodosResponse = dbQuery {
        val title = request.title
        val isDone = request.isDone

//        val result = ToDos.select { ToDos.title like "%${title}%" }
//            .orderBy(ToDos.id)
//            .map(::rowToToDo)

//        val result = ToDos.select {
//            buildList {
//                add(Op.TRUE)
//                if (title.isNotBlank()) {
//                    add(ToDos.title like "%${title}%")
//                }
//                if (isDone != null) {
//                    add(ToDos.isDone eq isDone)
//                }
//            }.reduce { a, b -> a and b }
//        }
//            .orderBy(ToDos.id)
//            .map(::toToDo)

        val res = TodosTable.selectAll()
        isDone?.let {
            res.andWhere { TodosTable.isDone eq isDone }
        }
        title.isBlank().let {
            res.andWhere { TodosTable.title like "%${title}%" }
        }

        DbTodosResponse(
            data = res.orderBy(TodosTable.id).map(::fromRow),
            isSuccess = true
        )
    }
}