package ru.shvets.todolist.entities

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.shvets.todolist.models.ToDo
import ru.shvets.todolist.models.ToDoId

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  10.05.2023 09:27
 */

object TodosTable : Table(name = "todos") {
    val id: Column<Long> = long("id").autoIncrement().uniqueIndex()
    val title: Column<String> = varchar("title", 124)
    val isDone: Column<Boolean> = bool("is_done")

    override val primaryKey: PrimaryKey = PrimaryKey(id)

    fun TodosTable.all() = TodosTable.selectAll().map { fromRow(it) }

    fun TodosTable.insert(toDo: ToDo): ToDo? {
        return TodosTable.insert { toRow(it, toDo) }.resultedValues?.singleOrNull()?.let { fromRow(it) }
    }

    fun TodosTable.findById(id: ToDoId): ToDo? {
        return TodosTable.select { TodosTable.id eq id.asString().toLong() }.map { fromRow(it) }.singleOrNull()
    }

    fun toRow(it: UpdateBuilder<*>, toDo: ToDo) {
        it[TodosTable.title] = toDo.title
        it[TodosTable.isDone] = toDo.isDone
    }

    fun fromRow(row: ResultRow): ToDo =
        ToDo(
            id = ToDoId(row[TodosTable.id].toString()),
            title = row[TodosTable.title],
            isDone = row[TodosTable.isDone]
        )
}