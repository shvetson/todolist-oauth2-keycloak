package repositories.todo

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import database.DatabaseFactory.dbQuery
import entities.TodosTable
import entities.TodosTable.fromRow
import models.requests.todo.TodoFilterRequest
import models.requests.todo.TodoIdRequest
import models.requests.todo.TodoRequest
import repositories.base.ITodoRepository

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  15.05.2023 11:43
 */

class TodoRepository() : ITodoRepository {
    override suspend fun createTodo(request: TodoRequest): TodoResponse = dbQuery {
        val toDo = request.toDo
        val res = TodosTable.insert { toRow(it, toDo) }
            .resultedValues?.singleOrNull()?.let(::fromRow) ?: return@dbQuery TodoResponse.errorSave
        TodoResponse.success(res)
    }

    override suspend fun readTodo(request: TodoIdRequest): TodoResponse = dbQuery {
        val id = request.id
        val res = TodosTable.select { TodosTable.id eq (id.asString().toLong()) }
            .map(::fromRow)
            .singleOrNull() ?: return@dbQuery TodoResponse.errorNotFound
        TodoResponse.success(res)
    }

    override suspend fun updateTodo(request: TodoRequest): TodoResponse = dbQuery {
        val id = request.toDo.id
//        if (id == ToDoId.NONE) return@dbQuery TodoResponse.errorEmptyId
        val todo = runBlocking { readTodo(TodoIdRequest(id)).data } ?: return@dbQuery TodoResponse.errorNotFound
        val toDo = request.toDo
        val res: Boolean = TodosTable.update({ TodosTable.id eq (id.asString().toLong()) }) { toRow(it, toDo) } > 0
        TodoResponse.success(toDo, res)
    }

    override suspend fun deleteTodo(request: TodoIdRequest): TodoResponse = dbQuery {
        val id = request.id
        val todo = runBlocking { readTodo(request).data } ?: return@dbQuery TodoResponse.errorNotFound
        val res: Boolean = TodosTable.deleteWhere { TodosTable.id eq (id.asString().toLong()) } > 0
        TodoResponse.success(todo, res)
    }

    override suspend fun searchTodos(request: TodoFilterRequest): TodosResponse = dbQuery {
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
            res.andWhere { TodosTable.isDone eq request.isDone }
        }
        title.isBlank().let {
            res.andWhere { TodosTable.title like "%${title}%" }
        }

        TodosResponse(
            data = res.orderBy(TodosTable.id).map(::fromRow),
            isSuccess = true
        )
    }
}