package ru.shvets.todolist.biz.general

import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.model.todo.TodoState
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.chain

fun ICorChainDsl<TodoContext>.validation(block: ICorChainDsl<TodoContext>.() -> Unit) = chain {
    block()
    title = "Валидация"
    on { state == TodoState.RUNNING }
}
