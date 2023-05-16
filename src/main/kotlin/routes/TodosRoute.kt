package ru.shvets.todolist.routes

import io.konform.validation.Invalid
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.shvets.todolist.models.ToDo
import ru.shvets.todolist.models.ToDoId
import ru.shvets.todolist.repository.base.ITodoRepository
import ru.shvets.todolist.repository.todo.TodoFilterRequest
import ru.shvets.todolist.repository.todo.TodoIdRequest
import ru.shvets.todolist.repository.todo.TodoRepository
import ru.shvets.todolist.repository.todo.TodoRequest
import ru.shvets.todolist.validate.resultErrorEmptyId
import ru.shvets.todolist.validate.resultErrorSaving
import ru.shvets.todolist.validate.resultErrorValidation

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  09.05.2023 18:17
 */

fun Route.todosRouting() {
    val todoRepository: ITodoRepository = TodoRepository()

    route("todos") {

        post("create") {
            val request = call.receive<TodoRequest>()

            val validationResult =
                TodoRequest(ToDo(title = request.toDo.title, isDone = request.toDo.isDone)).validate()
            if (validationResult is Invalid<TodoRequest>) {
                call.respond(HttpStatusCode.Conflict, resultErrorValidation(validationResult))
                return@post
            }

            val response = todoRepository.createTodo(request)
            if (response.data == null) {
                call.respond(HttpStatusCode.BadRequest, resultErrorSaving)
                return@post
            }

            call.respond(HttpStatusCode.Created, response)
        }

        post("read") {
            val request = call.receive<TodoIdRequest>()

            request.id.takeIf { it != ToDoId.NONE }?.let {
                val response = todoRepository.readTodo(request)
                call.respond(HttpStatusCode.OK, response)
            } ?: call.respond(HttpStatusCode.BadRequest, resultErrorEmptyId)
        }

        post("update") {
            val request = call.receive<TodoRequest>()

            val validationResult =
                TodoRequest(ToDo(title = request.toDo.title, isDone = request.toDo.isDone)).validate()
            if (validationResult is Invalid<TodoRequest>) {
                call.respond(HttpStatusCode.Conflict, resultErrorValidation(validationResult))
                return@post
            }

            request.toDo.id.takeIf { it != ToDoId.NONE }?.let {
                val response = todoRepository.updateTodo(request)
                call.respond(HttpStatusCode.OK, response)
            } ?: call.respond(HttpStatusCode.BadRequest, resultErrorEmptyId)
        }

        post("delete") {
            val request = call.receive<TodoIdRequest>()

            request.id.takeIf { it != ToDoId.NONE }?.let {
                val response = todoRepository.deleteTodo(request)
                call.respond(HttpStatusCode.OK, response)
            } ?: call.respond(HttpStatusCode.BadRequest, resultErrorEmptyId)
        }

        post("search"){
            val request = call.receive<TodoFilterRequest>()
            val response = todoRepository.searchTodos(request)
            call.respond(HttpStatusCode.OK, response)
        }
    }
}