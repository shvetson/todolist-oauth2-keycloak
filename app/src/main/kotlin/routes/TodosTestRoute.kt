package ru.shvets.todolist.app.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.shvets.todolist.app.TodoAppSettings
import ru.shvets.todolist.app.controllers.*

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  09.05.2023 18:17
 */

fun Route.todosTestRouting(appSettings: TodoAppSettings) {
    route("todos") {
        post("create") {
            call.createTodo(appSettings)
        }
        post("read") {
            call.readTodo(appSettings)
        }
        post("update") {
            call.updateTodo(appSettings)
        }
        post("delete") {
            call.deleteTodo(appSettings)
        }
        post("search") {
            call.searchTodo(appSettings)
        }
    }
}