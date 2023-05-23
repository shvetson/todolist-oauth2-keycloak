package ru.shvets.todolist.app.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.shvets.api.v1.models.*
import ru.shvets.todolist.app.TodoAppSettings
import ru.shvets.todolist.common.models.todo.TodoCommand

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  09.05.2023 18:17
 */

fun Route.todosTestRouting(appSettings: TodoAppSettings) {
    route("todos") {
        post("create") {
            call.process<TodoCreateRequest, TodoCreateResponse>(appSettings, "todo-create", TodoCommand.CREATE)
        }
        post("read") {
            call.process<TodoReadRequest, TodoReadResponse>(appSettings, "todo-read", TodoCommand.READ)
        }
        post("update") {
            call.process<TodoUpdateRequest, TodoUpdateResponse>(appSettings, "todo-update", TodoCommand.UPDATE)
        }
        post("delete") {
            call.process<TodoDeleteRequest, TodoDeleteResponse>(appSettings, "todo-delete", TodoCommand.DELETE)
        }
        post("search") {
            call.process<TodoSearchRequest, TodoSearchResponse>(appSettings, "todo-search", TodoCommand.SEARCH)
        }
    }
}