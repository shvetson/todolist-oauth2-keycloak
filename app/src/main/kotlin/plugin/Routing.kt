package ru.shvets.todolist.app.plugin

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import ru.shvets.todolist.app.TodoAppSettings
import ru.shvets.todolist.app.route.todosRouting

fun Application.configureRouting(appSettings: TodoAppSettings) {
    routing {

        route("/api/v1") {
            authenticate("auth-jwt") {
                todosRouting(appSettings)
            }
        }

        swaggerUI(path = "/api", swaggerFile = "specs/spec-todos-v1.yaml")
    }
}