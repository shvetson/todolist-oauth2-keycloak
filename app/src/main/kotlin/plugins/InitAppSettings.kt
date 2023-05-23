package ru.shvets.todolist.app.plugins

import io.ktor.server.application.*
import org.slf4j.LoggerFactory
import ru.shvets.todolist.app.TodoAppSettings
import ru.shvets.todolist.app.repositories.TodoRepository
import ru.shvets.todolist.biz.TodoProcessor
import ru.shvets.todolist.common.TodoCorSettings

fun Application.initAppSettings(): TodoAppSettings {
    val corSettings = TodoCorSettings(
        repo = TodoRepository(),
    )
    return TodoAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = TodoProcessor(corSettings),
    )
}