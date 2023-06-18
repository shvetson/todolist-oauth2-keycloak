package ru.shvets.todolist.biz.permission

import ru.shvets.todolist.auth.checkPermitted
import ru.shvets.todolist.auth.resolveRelationsTo
import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.helper.fail
import ru.shvets.todolist.common.model.todo.TodoError
import ru.shvets.todolist.common.model.todo.TodoState
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.chain
import ru.shvets.todolist.lib.cor.worker

fun ICorChainDsl<TodoContext>.accessValidation(title: String) = chain {
    this.title = title
    description = "Вычисление прав доступа по группе принципала и таблице прав доступа"
    on { state == TodoState.RUNNING }
    worker("Вычисление отношения объявления к принципалу") {
        todoRepoRead.principalRelations = todoRepoRead.resolveRelationsTo(principal)
    }
    worker("Вычисление доступа к todos") {
        permitted = checkPermitted(command, todoRepoRead.principalRelations, permissionsChain)
    }
    worker {
        this.title = "Валидация прав доступа"
        description = "Проверка наличия прав для выполнения операции"
        on { !permitted }
        handle {
            fail(TodoError(description = "User is not allowed to perform this operation"))
        }
    }
}