package ru.shvets.todolist.common.models

import kotlinx.datetime.Instant
import ru.shvets.todolist.common.helpers.NONE
import ru.shvets.todolist.common.models.todo.*
import ru.shvets.todolist.common.repo.todo.ITodoRepository

data class TodoContext(
    var command: TodoCommand = TodoCommand.NONE,
    var state: TodoState = TodoState.NONE,
    val errors: MutableList<TodoError> = mutableListOf(),

    var todoRepo: ITodoRepository = ITodoRepository.NONE,
    var todoRepoRead: Todo = Todo(),
    var todoRepoPrepare: Todo = Todo(),
    var todoRepoDone: Todo = Todo(),
    var todosRepoDone: MutableList<Todo> = mutableListOf(),

    var timeStart: Instant = Instant.NONE,
    var todoRequest: Todo = Todo(),
    var todoFilterRequest: TodoFilter = TodoFilter(),

    var todoValidating: Todo = Todo(),
    var todoFilterValidating: TodoFilter = TodoFilter(),

    var todoValidated: Todo = Todo(),
    var todoFilterValidated: TodoFilter = TodoFilter(),

    var todoResponse: Todo = Todo(),
    var todosResponse: MutableList<Todo> = mutableListOf(),
)
