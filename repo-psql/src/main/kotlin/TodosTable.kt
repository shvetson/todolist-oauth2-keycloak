package ru.shvets.todolist.repo.psql

import kotlinx.datetime.*
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.shvets.todolist.common.helper.NONE
import ru.shvets.todolist.common.model.todo.Todo
import ru.shvets.todolist.common.model.todo.TodoId
import ru.shvets.todolist.common.model.todo.TodoUserId

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  10.05.2023 09:27
 */

object TodosTable : Table(name = "todos") {
    //    val id: Column<Long> = long("id").autoIncrement().uniqueIndex()
    val id = varchar("id", 128).uniqueIndex()
    val title: Column<String> = varchar("title", 128)
    val isDone: Column<Boolean> = bool("is_done")
    val created = datetime("created")
    val updated = datetime("updated")
    val ownerId = varchar("owner_id", 128)

    override val primaryKey: PrimaryKey = PrimaryKey(id)

    fun toRow(it: UpdateBuilder<*>, todo: Todo, randomUuid: () -> String) {
        it[id] = todo.id.takeIf { it != TodoId.NONE }?.asString() ?: randomUuid()
        it[title] = todo.title
        it[isDone] = todo.isDone
        it[created] = (todo.created.takeIf { it != Instant.NONE } ?: Clock.System.now()).toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime()
        it[updated] = (todo.updated.takeIf { it != Instant.NONE } ?: Clock.System.now()).toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime()
        it[ownerId] = todo.ownerId.asString()
    }

    fun fromRow(result: ResultRow): Todo =
        Todo(
            id = TodoId(result[id].toString()),
            title = result[title],
            isDone = result[isDone],
            created = result[created].toKotlinLocalDateTime().toInstant(TimeZone.currentSystemDefault()),
            updated = result[updated].toKotlinLocalDateTime().toInstant(TimeZone.currentSystemDefault()),
            ownerId = TodoUserId(result[ownerId].toString()),
        )
}