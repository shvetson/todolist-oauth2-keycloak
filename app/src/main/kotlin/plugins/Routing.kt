package ru.shvets.todolist.app.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import ru.shvets.todolist.app.routes.authRouting
import ru.shvets.todolist.app.routes.todosRouting

fun Application.configureRouting() {
    routing {
        authRouting()

        authenticate {
            route("api/v1") {
                todosRouting()
            }
        }
    }
}