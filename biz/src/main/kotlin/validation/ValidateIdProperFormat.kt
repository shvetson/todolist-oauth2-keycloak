package ru.shvets.todolist.biz.validation

import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.helpers.fail
import ru.shvets.todolist.common.helpers.resultErrorValidation
import ru.shvets.todolist.common.models.todo.TodoId
import ru.shvets.todolist.lib.cor.ICorChainDsl
import ru.shvets.todolist.lib.cor.worker

fun ICorChainDsl<TodoContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в TodoId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { todoValidating.id != TodoId.NONE && !todoValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = todoValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            resultErrorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}