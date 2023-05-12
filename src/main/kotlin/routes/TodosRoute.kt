package ru.shvets.todolist.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import ru.shvets.todolist.models.ToDo
import ru.shvets.todolist.models.ToDoDto
import ru.shvets.todolist.repositories.ToDoRepository
import ru.shvets.todolist.repositories.ToDoRepositoryImpl

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  09.05.2023 18:17
 */

fun Route.todosRouting() {
    val repository: ToDoRepository = ToDoRepositoryImpl()

    route("todos") {

        get() {
            val list: List<ToDo> = repository.getAllToDo() ?: emptyList()
            call.respond(list)
        }

        post() {
            val toDoDraft = call.receive<ToDoDto>()
            val todo = repository.addToDo(toDoDraft)
            if (todo == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Error saving a new todo"
                )
                return@post
            }
            call.respond(
                HttpStatusCode.OK,
                hashMapOf("Todo" to todo)
            )
        }

        get("{id}") {
            val id = call.parameters.getOrFail("id").toLongOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Wrong Id"
                )
                return@get
            }

            val todo = repository.getToDo(id)
            if (todo == null) {
                call.respond(
                    HttpStatusCode.NotFound,
                    "Found no todo for the provided id $id"
                )
            } else {
                call.respond(todo)
            }
        }

        put("{id}") {
            val todoId = call.parameters["id"]?.toLongOrNull()
            val toDoDraft = call.receive<ToDoDto>()

            if (todoId == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Id parameter has to be a number!"
                )
                return@put
            }

            val updated = repository.updateToDo(todoId, toDoDraft)
            if (updated) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(
                    HttpStatusCode.NotFound,
                    "Found no todo with id $todoId"
                )
            }
        }

        delete("{id}") {
            val todoId = call.parameters["id"]?.toLongOrNull()

            if (todoId == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Id parameter has to be a number!"
                )
                return@delete
            }

            val removed = repository.removeTodo(todoId)
            if (removed) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(
                    HttpStatusCode.NotFound,
                    "Found no todo with id $todoId"
                )
            }
        }
    }
}