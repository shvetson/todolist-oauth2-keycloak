package ru.shvets.todolist.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import ru.shvets.todolist.routes.authRouting
import ru.shvets.todolist.routes.todosRouting

fun Application.configureRouting() {
    routing {
        authRouting()

        authenticate {
            todosRouting()
        }
    }
}