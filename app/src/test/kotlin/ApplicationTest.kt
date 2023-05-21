package ru.shvets.todolist.app

import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import ru.shvets.todolist.app.database.DatabaseFactory.db
import ru.shvets.todolist.common.models.todo.Todo
import ru.shvets.todolist.common.models.todo.TodoId
import ru.shvets.todolist.app.service.Todos
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
        val tdId = TodoId(id)

        val id2 = "0"
        val tdId2 = TodoId(id2)
        transaction(db) {
            val toDo = todos.findById(tdId)
            assertEquals(tdId, toDo?.id)

            val toDo2 = todos.findById(tdId2)
            assertNotEquals(tdId2, toDo2?.id)
        }
    }
}

fun todo(block: Todo.() -> Unit): Todo {
    val td = Todo()
    td.block()
    return td
}
