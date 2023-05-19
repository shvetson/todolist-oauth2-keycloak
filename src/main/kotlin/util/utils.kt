package util

import models.ToDo

val ToDo.toConsole: String
    get() = "ToDo[id=$id, title=$title, isDone=$isDone]"


fun <T> T.ext(block: T.()-> Unit): T {
    block()
    return this
}

fun td(block: ToDo.() -> Unit) = ToDo().apply(block)