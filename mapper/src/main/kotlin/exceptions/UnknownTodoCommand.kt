package ru.shvets.todolist.mapper.exceptions

import ru.shvets.todolist.common.models.todo.TodoCommand

class UnknownTodoCommand(command: TodoCommand) : Throwable("Wrong command $command at mapping toTransport stage")
