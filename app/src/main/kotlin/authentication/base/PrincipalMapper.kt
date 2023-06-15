package ru.shvets.todolist.app.authentication.base

import io.ktor.server.auth.jwt.*
import ru.shvets.todolist.app.authentication.base.KtorAuthConfig.Companion.F_NAME_CLAIM
import ru.shvets.todolist.app.authentication.base.KtorAuthConfig.Companion.GROUPS_CLAIM
import ru.shvets.todolist.app.authentication.base.KtorAuthConfig.Companion.ID_CLAIM
import ru.shvets.todolist.app.authentication.base.KtorAuthConfig.Companion.L_NAME_CLAIM
import ru.shvets.todolist.app.authentication.base.KtorAuthConfig.Companion.M_NAME_CLAIM
import ru.shvets.todolist.common.model.todo.TodoUserId
import ru.shvets.todolist.common.permission.TodoPrincipalModel
import ru.shvets.todolist.common.permission.TodoUserGroups

fun JWTPrincipal?.toModel() = this?.run {
    TodoPrincipalModel(
        id = payload.getClaim(ID_CLAIM).asString()?.let { TodoUserId(it) } ?: TodoUserId.NONE,
        fname = payload.getClaim(F_NAME_CLAIM).asString() ?: "",
        mname = payload.getClaim(M_NAME_CLAIM).asString() ?: "",
        lname = payload.getClaim(L_NAME_CLAIM).asString() ?: "",
        groups = payload
            .getClaim(GROUPS_CLAIM)
            ?.asList(String::class.java)
            ?.mapNotNull {
                when(it) {
                    "USER" -> TodoUserGroups.USER
                    else -> null
                }
            }?.toSet() ?: emptySet()
    )
} ?: TodoPrincipalModel.NONE