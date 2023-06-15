package ru.shvets.todolist.app.plugin

import io.ktor.server.application.*
import ru.shvets.todolist.app.TodoAppSettings
import ru.shvets.todolist.app.authentication.base.KtorAuthConfig
import ru.shvets.todolist.app.config.DbType
import ru.shvets.todolist.app.config.getDatabaseConf
import ru.shvets.todolist.biz.TodoProcessor
import ru.shvets.todolist.common.TodoCorSettings

fun Application.initAppSettings(): TodoAppSettings {
    val corSettings = TodoCorSettings(
        repo = getDatabaseConf(DbType.PROD),
    )
    return TodoAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = TodoProcessor(corSettings),
        auth = initAppAuth(),
    )
}

private fun Application.initAppAuth(): KtorAuthConfig = KtorAuthConfig(
    secret = environment.config.propertyOrNull("jwt.secret")?.getString() ?: "",
    issuer = environment.config.property("jwt.issuer").getString(),
    audience = environment.config.property("jwt.audience").getString(),
    realm = environment.config.property("jwt.realm").getString(),
    clientId = environment.config.property("jwt.clientId").getString(),
    certUrl = environment.config.propertyOrNull("jwt.certUrl")?.getString(),
)