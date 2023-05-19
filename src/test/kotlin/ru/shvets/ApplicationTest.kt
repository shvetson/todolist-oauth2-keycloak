package ru.shvets

import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import database.DatabaseFactory.db
import models.ToDo
import models.ToDoId
import models.Todos
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ApplicationTest {
    private val todos = Todos()

    @Test
    fun `add new todo`() {
        val toDo = todo {
            title = "Test adding new item"
            isDone = false
        }
        transaction(db) {
            val added = todos.add(toDo)
            assertEquals(toDo.title, added?.title)
        }
    }

    @Test
    fun `is empty db`() {
        transaction(db) {
            assertEquals(true, todos.all().isNotEmpty())
        }
    }

    @Test
    fun `find todo by id`() {
        val id = "1"
        val tdId = ToDoId(id)

        val id2 = "0"
        val tdId2 = ToDoId(id2)
        transaction(db) {
            val toDo = todos.findById(tdId)
            assertEquals(tdId, toDo?.id)

            val toDo2 = todos.findById(tdId2)
            assertNotEquals(tdId2, toDo2?.id)
        }
    }
}

fun todo(block: ToDo.() -> Unit): ToDo {
    val td = ToDo()
    td.block()
    return td
}
