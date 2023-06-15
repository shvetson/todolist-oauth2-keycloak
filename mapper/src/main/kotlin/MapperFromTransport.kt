package ru.shvets.todolist.mapper

import ru.shvets.api.v1.models.*
import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.model.todo.Todo
import ru.shvets.todolist.common.model.todo.TodoCommand
import ru.shvets.todolist.common.model.todo.TodoFilter
import ru.shvets.todolist.common.model.todo.TodoId
import ru.shvets.todolist.mapper.exceptions.UnknownRequestClass

fun TodoContext.fromTransport(request: IRequest) = when (request) {
    is TodoCreateRequest -> fromTransport(request)
    is TodoReadRequest -> fromTransport(request)
    is TodoUpdateRequest -> fromTransport(request)
    is TodoDeleteRequest -> fromTransport(request)
    is TodoSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toTodoId() = this?.let { TodoId(it) } ?: TodoId.NONE

fun TodoContext.fromTransport(request: TodoCreateRequest) {
    command = TodoCommand.CREATE
    todoRequest = request.todo?.toInternal() ?: Todo()
}

fun TodoContext.fromTransport(request: TodoReadRequest) {
    command = TodoCommand.READ
    todoRequest = request.todo.toInternal()
}

fun TodoContext.fromTransport(request: TodoUpdateRequest) {
    command = TodoCommand.UPDATE
    todoRequest = request.todo?.toInternal() ?: Todo()
}

fun TodoContext.fromTransport(request: TodoDeleteRequest) {
    command = TodoCommand.DELETE
    todoRequest = request.todo.toInternal()
}

fun TodoContext.fromTransport(request: TodoSearchRequest) {
    command = TodoCommand.SEARCH
    todoFilterRequest = request.todoFilter.toInternal()
}

private fun TodoCreateObject.toInternal(): Todo = Todo(
    title = this.title ?: "",
    isDone = this.isDone ?: false,
)

private fun TodoReadObject?.toInternal(): Todo {
    return if (this != null) {
        Todo(id = id.toTodoId())
    } else {
        Todo.NONE
    }
}

private fun TodoUpdateObject.toInternal(): Todo = Todo(
    id = this.id.toTodoId(),
    title = this.title ?: "",
    isDone = this.isDone ?: false,
)

private fun TodoDeleteObject?.toInternal(): Todo {
    return if (this != null) {
        Todo(id = id.toTodoId())
    } else {
        Todo.NONE
    }
}

private fun TodoSearchFilter?.toInternal(): TodoFilter = TodoFilter(
    searchString = this?.searchString ?: "",
    isDone = this?.isDone,
)