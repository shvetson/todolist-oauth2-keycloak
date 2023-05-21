package ru.shvets.todolist.common.helpers

import ru.shvets.todolist.common.models.todo.Todo


val Todo.toConsole: String
    get() = "ToDo[id=$id, title=$title, isDone=$isDone]"


fun <T> T.ext(block: T.()-> Unit): T {
    block()
    return this
}

fun td(block: Todo.() -> Unit) = Todo().apply(block)