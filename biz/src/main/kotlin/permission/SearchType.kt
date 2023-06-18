package ru.shvets.todolist.biz.permission

import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.model.todo.TodoSearchPermissions
import ru.shvets.todolist.common.model.todo.TodoState
import ru.shvets.todolist.common.permission.TodoUserPermissions
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.chain
import ru.shvets.todolist.lib.cor.worker

fun ICorChainDsl<TodoContext>.searchTypes(title: String) = chain {
    this.title = title
    description = "Добавление ограничений в поисковый запрос согласно правам доступа и др. политикам"
    on { state == TodoState.RUNNING }
    worker("Определение типа поиска") {
        todoFilterValidated.searchPermissions = setOfNotNull(
            TodoSearchPermissions.OWN.takeIf { permissionsChain.contains(TodoUserPermissions.SEARCH_OWN) },
            TodoSearchPermissions.PUBLIC.takeIf { permissionsChain.contains(TodoUserPermissions.SEARCH_PUBLIC) },
            TodoSearchPermissions.REGISTERED.takeIf { permissionsChain.contains(TodoUserPermissions.SEARCH_REGISTERED) },
        ).toMutableSet()
    }
}