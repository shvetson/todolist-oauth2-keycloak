package ru.shvets.todolist.common

import kotlinx.datetime.Instant
import ru.shvets.todolist.common.helper.NONE
import ru.shvets.todolist.common.model.todo.Todo
import ru.shvets.todolist.common.model.todo.*
import ru.shvets.todolist.common.permission.TodoPrincipalModel
import ru.shvets.todolist.common.permission.TodoUserPermissions
import ru.shvets.todolist.common.repo.todo.ITodoRepository

data class TodoContext(
    var command: TodoCommand = TodoCommand.NONE,
    var state: TodoState = TodoState.NONE,
    val errors: MutableList<TodoError> = mutableListOf(),
    var settings: TodoCorSettings = TodoCorSettings.NONE,
    var todoRepo: ITodoRepository = ITodoRepository.NONE,
    var timeStart: Instant = Instant.NONE,

    var principal: TodoPrincipalModel = TodoPrincipalModel.NONE,
    val permissionsChain: MutableSet<TodoUserPermissions> = mutableSetOf(),
    var permitted: Boolean = false,

    var todoRequest: Todo = Todo(),
    var todoValidating: Todo = Todo(),
    var todoValidated: Todo = Todo(),

    var todoFilterRequest: TodoFilter = TodoFilter(),
    var todoFilterValidating: TodoFilter = TodoFilter(),
    var todoFilterValidated: TodoFilter = TodoFilter(),

    var todoRepoRead: Todo = Todo(),
    var todoRepoPrepare: Todo = Todo(),
    var todoRepoDone: Todo = Todo(),
    var todosRepoDone: MutableList<Todo> = mutableListOf(),

    var todoResponse: Todo = Todo(),
    var todosResponse: MutableList<Todo> = mutableListOf(),
)