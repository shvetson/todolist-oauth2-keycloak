package ru.shvets.todolist.biz.validation

import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.helper.fail
import ru.shvets.todolist.common.helper.resultErrorValidation
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.worker

fun ICorChainDsl<TodoContext>.validateIdNotEmpty(title: String) = worker {
    this.title = title
    on { todoValidating.id.asString().isEmpty() }
    handle {
        fail(
            resultErrorValidation(
                field = "id",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}