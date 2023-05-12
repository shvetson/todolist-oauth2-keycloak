package ru.shvets.todolist.repositories

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import ru.shvets.todolist.database.DatabaseFactory.dbQuery
import ru.shvets.todolist.entities.ToDos
import ru.shvets.todolist.models.ToDo
import ru.shvets.todolist.models.ToDoDto

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  10.05.2023 10:07
 */

class PostgresToDoRepositoryImpl : ToDoRepository {

    override suspend fun getAllToDo(): List<ToDo> = dbQuery {
        ToDos.selectAll().map(::rowToToDo)
    }

    override suspend fun getToDo(id: Long): ToDo? = dbQuery {
        ToDos.select { ToDos.id.eq(id) }
            .map(::rowToToDo)
            .singleOrNull()
    }

    override suspend fun addToDo(draft: ToDoDto): ToDo? = dbQuery {
        ToDos.insert { td ->
            td[ToDos.title] = draft.title
            td[ToDos.isDone] = draft.isDone
        }.resultedValues?.singleOrNull()?.let(::rowToToDo)
    }

    override suspend fun removeTodo(id: Long): Boolean = dbQuery {
        ToDos.deleteWhere { ToDos.id.eq(id) } > 0
    }

    override suspend fun updateToDo(id: Long, draft: ToDoDto): Boolean = dbQuery {
        ToDos.update({ ToDos.id.eq(id) }) { td ->
            td[ToDos.title] = draft.title
            td[ToDos.isDone] = draft.isDone
        } > 0
    }

    private fun rowToToDo(row: ResultRow): ToDo =
        ToDo(
            id = row[ToDos.id],
            title = row[ToDos.title],
            isDone = row[ToDos.isDone]
        )
}