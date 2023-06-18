package ru.shvets.todolist.app.route

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import org.slf4j.LoggerFactory
import ru.shvets.api.v1.models.IRequest
import ru.shvets.api.v1.models.IResponse
import ru.shvets.todolist.app.TodoAppSettings
import ru.shvets.todolist.app.base.toModel
import ru.shvets.todolist.common.TodoContext
import ru.shvets.todolist.common.helper.asTodoError
import ru.shvets.todolist.common.model.todo.TodoCommand
import ru.shvets.todolist.common.model.todo.TodoState
import ru.shvets.todolist.mapper.fromTransport
import ru.shvets.todolist.mapper.toTransportTodo

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.process(
    appSettings: TodoAppSettings,
    logId: String,
    command: TodoCommand? = null,
) {
    val logger = LoggerFactory.getLogger(javaClass)
    val ctx = TodoContext(timeStart = Clock.System.now())
    val processor = appSettings.processor

    try {
        val id = "${logId}-request"
        logger.info("ID $id")
        ctx.principal = principal<JWTPrincipal>().toModel()
        val request = receive<Q>()
        ctx.fromTransport(request)
        logger.info("$command request is got")
        processor.exec(ctx)
        logger.info("$command request is handled")
        respond(ctx.toTransportTodo())
    } catch (e: Throwable) {
        val id = "${logId}-failure"
        logger.info("ID $id")
        command?.also { ctx.command = it }
        logger.error("$command handling failed")
        ctx.state = TodoState.FAILING
        ctx.errors.add(e.asTodoError())
        processor.exec(ctx)
        respond(ctx.toTransportTodo())
    }
}