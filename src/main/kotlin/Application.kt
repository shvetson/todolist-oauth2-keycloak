package ru.shvets.todolist

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.event.*
import ru.shvets.todolist.database.DatabaseFactory
import ru.shvets.todolist.plugins.configureMonitoring
import ru.shvets.todolist.plugins.configureRouting
import ru.shvets.todolist.plugins.configureSecurity
import ru.shvets.todolist.plugins.configureSerialization

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {

    configureMonitoring()
    configureSerialization()
    configureSecurity()
    DatabaseFactory.init()
    configureRouting()
}