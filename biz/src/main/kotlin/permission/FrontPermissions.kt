package ru.shvets.todolist.biz.permission

import ru.shvets.todolist.auth.resolveFrontPermissions
import ru.shvets.todolist.auth.resolveRelationsTo
import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.model.todo.TodoState
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.worker

fun ICorChainDsl<TodoContext>.frontPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление разрешений пользователей для фронтенда"

    on { state == TodoState.RUNNING }

    handle {
        todoRepoDone.permissionsClient.addAll(
            resolveFrontPermissions(
                permissionsChain,
                // Повторно вычисляем отношения, поскольку они могли измениться при выполении операции
                todoRepoDone.resolveRelationsTo(principal)
            )
        )

        for (todo in todosRepoDone) {
            todo.permissionsClient.addAll(
                resolveFrontPermissions(
                    permissionsChain,
                    todo.resolveRelationsTo(principal)
                )
            )
        }
    }
}
