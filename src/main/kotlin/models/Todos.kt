package models

import entities.TodosTable
import entities.TodosTable.all
import entities.TodosTable.findById
import entities.TodosTable.insert
import models.ToDo
import models.ToDoId

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