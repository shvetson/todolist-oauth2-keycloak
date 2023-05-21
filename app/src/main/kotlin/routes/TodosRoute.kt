package ru.shvets.todolist.app.routes

import io.konform.validation.Invalid
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.shvets.todolist.common.models.todo.Todo
import ru.shvets.todolist.common.models.todo.TodoId
import ru.shvets.todolist.common.repo.todo.DbTodoFilterRequest
import ru.shvets.todolist.common.repo.todo.DbTodoIdRequest
import ru.shvets.todolist.common.repo.todo.DbTodoRequest
import ru.shvets.todolist.common.repo.todo.ITodoRepository
import ru.shvets.todolist.app.repositories.TodoRepository
import ru.shvets.todolist.common.validate.resultErrorEmptyId
import ru.shvets.todolist.common.validate.resultErrorSaving
import ru.shvets.todolist.common.validate.resultErrorValidation

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  09.05.2023 18:17
 */

fun Route.todosRouting() {
    val todoRepository: ITodoRepository = TodoRepository()

    route("todos") {

        post("create") {
            val request = call.receive<DbTodoRequest>()

            val toDo = Todo(
                title = request.toDo.title,
                isDone = request.toDo.isDone
            )

            val validationResult = DbTodoRequest(toDo).validate()
            if (validationResult is Invalid<DbTodoRequest>) {
                call.respond(HttpStatusCode.Conflict, resultErrorValidation(validationResult))
                return@post
            }

            val response = todoRepository.createTodo(request)
            if (response.data == null) {
                call.respond(HttpStatusCode.BadRequest, resultErrorSaving)
                return@post
            } else {
                call.respond(HttpStatusCode.Created, response)
            }
        }

        post("read") {
            val request = call.receive<DbTodoIdRequest>()

            request.id.takeIf { it != TodoId.NONE }?.let {
                val response = todoRepository.readTodo(request)
                call.respond(HttpStatusCode.OK, response)
            } ?: call.respond(HttpStatusCode.BadRequest, resultErrorEmptyId)
        }

        post("update") {
            val request = call.receive<DbTodoRequest>()

            val validationResult =
                DbTodoRequest(Todo(title = request.toDo.title, isDone = request.toDo.isDone)).validate()
            if (validationResult is Invalid<DbTodoRequest>) {
                call.respond(HttpStatusCode.Conflict, resultErrorValidation(validationResult))
                return@post
            }

            request.toDo.id.takeIf { it != TodoId.NONE }?.let {
                val response = todoRepository.updateTodo(request)
                call.respond(HttpStatusCode.OK, response)
            } ?: call.respond(HttpStatusCode.BadRequest, resultErrorEmptyId)
        }

        post("delete") {
            val request = call.receive<DbTodoIdRequest>()

            request.id.takeIf { it != TodoId.NONE }?.let {
                val response = todoRepository.deleteTodo(request)
                call.respond(HttpStatusCode.OK, response)
            } ?: call.respond(HttpStatusCode.BadRequest, resultErrorEmptyId)
        }

        post("search") {
            val request = call.receive<DbTodoFilterRequest>()
            val response = todoRepository.searchTodos(request)
            call.respond(HttpStatusCode.OK, response)
        }
    }
}