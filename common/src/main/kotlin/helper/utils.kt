package ru.shvets.todolist.common.helper

import ru.shvets.todolist.common.model.todo.Todo


val Todo.toConsole: String
    get() = "ToDo[id=$id, title=$title, isDone=$isDone]"


fun <T> T.ext(block: T.()-> Unit): T {
    block()
    return this
}

fun td(block: Todo.() -> Unit) = Todo().apply(block)