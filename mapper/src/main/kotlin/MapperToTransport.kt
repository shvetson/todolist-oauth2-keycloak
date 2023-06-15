package ru.shvets.todolist.mapper

import kotlinx.datetime.Instant
import ru.shvets.api.v1.models.*
import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.helper.NONE
import ru.shvets.todolist.common.model.todo.*
import ru.shvets.todolist.common.permission.TodoPermission
import ru.shvets.todolist.mapper.exceptions.UnknownTodoCommand

fun TodoContext.toTransportTodo(): IResponse = when (val cmd = command) {
    TodoCommand.CREATE -> toTransportCreate()
    TodoCommand.READ -> toTransportRead()
    TodoCommand.UPDATE -> toTransportUpdate()
    TodoCommand.DELETE -> toTransportDelete()
    TodoCommand.SEARCH -> toTransportSearch()
    TodoCommand.NONE -> throw UnknownTodoCommand(cmd)
}

fun TodoContext.toTransportCreate() = TodoCreateResponse(
    responseType = "create",
    result = if (state == TodoState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    todo = todoResponse.toTransportTodo()
)

fun TodoContext.toTransportRead() = TodoReadResponse(
    responseType = "read",
    result = if (state == TodoState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    todo = todoResponse.toTransportTodo()
)

fun TodoContext.toTransportUpdate() = TodoUpdateResponse(
    responseType = "update",
    result = if (state == TodoState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    todo = todoResponse.toTransportTodo()
)

fun TodoContext.toTransportDelete() = TodoDeleteResponse(
    responseType = "delete",
    result = if (state == TodoState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    todo = todoResponse.toTransportTodo()
)

fun TodoContext.toTransportSearch() = TodoSearchResponse(
    responseType = "search",
    result = if (state == TodoState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    todos = todosResponse.toTransportTodo()
)

fun List<Todo>.toTransportTodo(): List<TodoResponseObject>? = this
    .map { it.toTransportTodo() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Todo.toTransportTodo(): TodoResponseObject = TodoResponseObject(
    id = id.takeIf { it != TodoId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    isDone = isDone,
    created = created.takeIf { it != Instant.NONE }.toString(),
    updated = updated.takeIf { it != Instant.NONE }.toString(),
    ownerId = ownerId.takeIf { it != TodoUserId.NONE }?.asString(),
    permissions = permissionsClient.toTransportTodo(),
)

private fun Set<TodoPermission>.toTransportTodo(): Set<TodoPermissions>? = this
    .map { it.toTransportTodo() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun TodoPermission.toTransportTodo() = when (this) {
    TodoPermission.READ -> TodoPermissions.READ
    TodoPermission.UPDATE -> TodoPermissions.UPDATE
    TodoPermission.MAKE_VISIBLE_OWNER -> TodoPermissions.MAKE_VISIBLE_OWN
    TodoPermission.MAKE_VISIBLE_GROUP -> TodoPermissions.MAKE_VISIBLE_GROUP
    TodoPermission.MAKE_VISIBLE_PUBLIC -> TodoPermissions.MAKE_VISIBLE_PUBLIC
    TodoPermission.DELETE -> TodoPermissions.DELETE
}

private fun List<TodoError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportTodo() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun TodoError.toTransportTodo() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
)