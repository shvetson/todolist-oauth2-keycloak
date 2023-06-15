package ru.shvets.todolist.repo.psql

import com.benasher44.uuid.uuid4
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.shvets.todolist.repo.psql.TodosTable.fromRow
import ru.shvets.todolist.common.repo.todo.*

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  15.05.2023 11:43
 */

class TodoRepository(
    properties: SqlProperties,
    private val randomUuid: () -> String = { uuid4().toString() },
) : ITodoRepository {

    init {
        val driver = when {
            properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unknown driver for url ${properties.url} ")
        }

        val config = HikariConfig()
        config.apply {
            driverClassName = driver
            jdbcUrl = properties.url
            username = properties.user
            password = properties.password
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }

        Database.connect(HikariDataSource(config))

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(TodosTable)
//            SchemaUtils.drop(ToDosTable)
            SchemaUtils.createMissingTablesAndColumns(TodosTable)
        }
    }

    override suspend fun createTodo(request: DbTodoRequest): DbTodoResponse = dbQuery {
        val toDo = request.toDo
        val res = TodosTable.insert { toRow(it, toDo, randomUuid) }
            .resultedValues?.singleOrNull()?.let(::fromRow) ?: return@dbQuery DbTodoResponse.errorSave
        DbTodoResponse.success(res)
    }

    override suspend fun readTodo(request: DbTodoIdRequest): DbTodoResponse = dbQuery {
        val id = request.id
        val res = TodosTable.select { TodosTable.id eq id.asString() }
            .map(::fromRow)
            .singleOrNull() ?: return@dbQuery DbTodoResponse.errorNotFound
        DbTodoResponse.success(res)
    }

    override suspend fun updateTodo(request: DbTodoRequest): DbTodoResponse = dbQuery {
        val id = request.toDo.id
//        if (id == ToDoId.NONE) return@dbQuery TodoResponse.errorEmptyId
        runBlocking { readTodo(DbTodoIdRequest(id)).data } ?: return@dbQuery DbTodoResponse.errorNotFound
        val todo = request.toDo
        todo.apply { this.updated = Clock.System.now() }
        val res: Boolean = TodosTable.update({ TodosTable.id eq id.asString() }) { toRow(it, todo, randomUuid) } > 0
        DbTodoResponse.success(todo, res)
    }

    override suspend fun deleteTodo(request: DbTodoIdRequest): DbTodoResponse = dbQuery {
        val id = request.id
        val todo = runBlocking { readTodo(request).data } ?: return@dbQuery DbTodoResponse.errorNotFound
        val res: Boolean = TodosTable.deleteWhere { TodosTable.id eq id.asString() } > 0
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

    private suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction {
//                addLogger(StdOutSqlLogger)
                block()
            }
        }
}