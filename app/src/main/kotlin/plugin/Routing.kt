package ru.shvets.todolist.app.plugin

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.shvets.todolist.app.TodoAppSettings
import ru.shvets.todolist.app.route.todosRouting

fun Application.configureRouting(appSettings: TodoAppSettings) {
    routing {
//        authenticate("auth-jwt") {
            route("api/v1") {
                todosRouting(appSettings)
            }
//        }
    }
}