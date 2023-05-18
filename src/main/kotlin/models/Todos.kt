package ru.shvets.todolist.models

import ru.shvets.todolist.entities.TodosTable
import ru.shvets.todolist.entities.TodosTable.all
import ru.shvets.todolist.entities.TodosTable.findById
import ru.shvets.todolist.entities.TodosTable.insert

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  18.05.2023 09:20
 */

class Todos() {

    fun all(): List<ToDo> {
        return TodosTable.all()
    }

    fun add(todo: ToDo): ToDo? {
        return TodosTable.insert(todo)
    }

    fun findById(id: ToDoId): ToDo? {
        return TodosTable.findById(id)
    }
}