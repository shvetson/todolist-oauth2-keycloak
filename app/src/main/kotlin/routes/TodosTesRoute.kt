package ru.shvets.todolist.app.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory
import ru.shvets.todolist.app.TodoAppSettings
import ru.shvets.todolist.app.repositories.TodoRepository
import ru.shvets.todolist.common.repo.todo.ITodoRepository

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  09.05.2023 18:17
 */

fun Route.todosTestRouting(appSettings: TodoAppSettings) {
    route("api/v1/todos") {
        val logger = LoggerFactory.getLogger(javaClass)

        post("create") {
            call.createTodo(appSettings, logger)
        }
        post("read") {
            call.createTodo(appSettings, logger)
        }
        post("update") {
            call.createTodo(appSettings, logger)
        }
        post("delete") {
            call.createTodo(appSettings, logger)
        }
        post("search") {
            call.createTodo(appSettings, logger)
        }

    }
}