package ru.shvets.todolist.entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  10.05.2023 09:27
 */

object ToDos : Table(name = "todos") {
    val id: Column<Long> = long("id").autoIncrement().uniqueIndex()
    val title: Column<String> = varchar("title", 124)
    val isDone: Column<Boolean> = bool("is_done")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}