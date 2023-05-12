package ru.shvets.todolist.entities

import org.jetbrains.exposed.sql.Table

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  10.05.2023 09:27
 */

object ToDoTable: Table(name = "todos") {
    val id = long("id").autoIncrement().uniqueIndex()
    val title = varchar("title", 124)
    val isDone = bool("is_done")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}