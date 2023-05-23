package ru.shvets.todolist.app.controllers

import io.ktor.server.application.*
import org.slf4j.Logger
import ru.shvets.api.v1.models.*
import ru.shvets.todolist.app.TodoAppSettings
import ru.shvets.todolist.common.models.todo.TodoCommand

suspend fun ApplicationCall.createTodo(appSettings: TodoAppSettings) =
    process<TodoCreateRequest, TodoCreateResponse>(appSettings, "todo-create", TodoCommand.CREATE)

suspend fun ApplicationCall.readTodo(appSettings: TodoAppSettings) =
    process<TodoReadRequest, TodoReadResponse>(appSettings, "todo-read", TodoCommand.READ)

suspend fun ApplicationCall.updateTodo(appSettings: TodoAppSettings) =
    process<TodoUpdateRequest, TodoUpdateResponse>(appSettings, "todo-update", TodoCommand.UPDATE)

suspend fun ApplicationCall.deleteTodo(appSettings: TodoAppSettings) =
    process<TodoDeleteRequest, TodoDeleteResponse>(appSettings, "todo-delete", TodoCommand.DELETE)

suspend fun ApplicationCall.searchTodo(appSettings: TodoAppSettings) =
    process<TodoSearchRequest, TodoSearchResponse>(appSettings, "todo-search", TodoCommand.SEARCH)