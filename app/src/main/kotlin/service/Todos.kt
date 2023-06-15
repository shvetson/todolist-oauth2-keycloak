package ru.shvets.todolist.app.service

import ru.shvets.todolist.app.entity.TodosTable
import ru.shvets.todolist.app.entity.TodosTable.all
import ru.shvets.todolist.app.entity.TodosTable.findById
import ru.shvets.todolist.app.entity.TodosTable.insert
import ru.shvets.todolist.common.model.todo.Todo
import ru.shvets.todolist.common.model.todo.TodoId

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  18.05.2023 09:20
 */

class Todos() {

    fun all(): List<Todo> {
        return TodosTable.all()
    }

    fun add(todo: Todo): Todo? {
        return TodosTable.insert(todo)
    }

    fun findById(id: TodoId): Todo? {
        return TodosTable.findById(id)
    }
}