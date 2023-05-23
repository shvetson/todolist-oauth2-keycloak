package ru.shvets.todolist.app.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import ru.shvets.todolist.app.TodoAppSettings
import ru.shvets.todolist.app.routes.authRouting
import ru.shvets.todolist.app.routes.todosRouting
import ru.shvets.todolist.app.routes.todosTestRouting

fun Application.configureRouting(appSettings: TodoAppSettings) {
    routing {
        authRouting()

        authenticate {
            todosRouting()
        }

        route("api/v1") {
            todosTestRouting(appSettings)
        }
    }
}