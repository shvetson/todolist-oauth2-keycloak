package ru.shvets.todolist.app.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import ru.shvets.todolist.app.routes.authRouting
import ru.shvets.todolist.app.routes.todosRouting
import ru.shvets.todolist.app.routes.todosTestRouting

fun Application.configureRouting() {
    routing {
        authRouting()

        authenticate {
            todosRouting()
        }

        todosTestRouting()
    }
}