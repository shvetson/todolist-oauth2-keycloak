package ru.shvets.todolist.app

import ru.shvets.todolist.app.authentication.base.KtorAuthConfig
import ru.shvets.todolist.biz.TodoProcessor
import ru.shvets.todolist.common.TodoCorSettings

data class TodoAppSettings(
    val appUrls: List<String> = emptyList(),
    val corSettings: TodoCorSettings,
    val processor: TodoProcessor = TodoProcessor(settings = corSettings),
    val auth: KtorAuthConfig = KtorAuthConfig.NONE,
)