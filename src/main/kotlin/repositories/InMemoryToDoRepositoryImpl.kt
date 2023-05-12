package ru.shvets.todolist.repositories

import ru.shvets.todolist.models.ToDo
import ru.shvets.todolist.models.ToDoDto

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  09.05.2023 19:19
 */

class InMemoryToDoRepository : ToDoRepository {

    private val todos = mutableListOf<ToDo>()

    override suspend fun getAllToDo(): List<ToDo>? {
        return todos
    }

    override suspend fun getToDo(id: Long): ToDo? {
        return todos.firstOrNull { it.id == id }
    }

    override suspend fun addToDo(draft: ToDoDto): ToDo {
        val todo = ToDo(
            id = (todos.size + 1).toLong(),
            title = draft.title,
            isDone = draft.isDone,
        )

        todos.add(todo)
        return todo
    }

    override suspend fun removeTodo(id: Long): Boolean {
        return todos.removeIf { it.id == id }
    }

    override suspend fun updateToDo(id: Long, draft: ToDoDto): Boolean {
        val todo = todos.firstOrNull { it.id == id }
            ?: return false

        todo.apply {
            this.title = draft.title
            this.isDone = draft.isDone
        }
        return true
    }
}