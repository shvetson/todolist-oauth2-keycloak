package ru.shvets.todolist.biz.general

import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.models.todo.TodoCommand
import ru.shvets.todolist.common.models.todo.TodoState
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.chain

fun ICorChainDsl<TodoContext>.operation(
    title: String,
    command: TodoCommand,
    block: ICorChainDsl<TodoContext>.() -> Unit,
) = chain {
    block()
    this.title = title
    on { this.command == command && state == TodoState.RUNNING }
}