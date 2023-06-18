package ru.shvets.todolist.biz.permission

import ru.shvets.todolist.auth.resolveChainPermissions
import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.model.todo.TodoState
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.worker

fun ICorChainDsl<TodoContext>.chainPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление прав доступа для групп пользователей"

    on { state == TodoState.RUNNING }

    handle {
        permissionsChain.addAll(resolveChainPermissions(principal.groups))
//        println("PRINCIPAL: $principal")
//        println("PERMISSIONS: $permissionsChain")
    }
}
