package ru.shvets.todolist.app.plugins

import io.ktor.server.plugins.callloging.*
import org.slf4j.event.*
import io.ktor.server.request.*
import io.ktor.server.application.*
import org.slf4j.Logger
import ru.shvets.todolist.app.TodoAppSettings

fun Application.configureMonitoring(appSettings: TodoAppSettings) {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
}