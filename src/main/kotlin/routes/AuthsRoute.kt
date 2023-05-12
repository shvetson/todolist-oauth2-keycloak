package ru.shvets.todolist.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.shvets.todolist.authentication.token.JwtConfig
import ru.shvets.todolist.authentication.LoginRequest
import ru.shvets.todolist.authentication.hashing.SHA256HashingService
import ru.shvets.todolist.repositories.UserRepositoryImpl
import ru.shvets.todolist.repositories.UserRepository

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  10.05.2023 16:41
 */

fun Route.userRouting() {
    val jwtConfig = JwtConfig()
    val hashing = SHA256HashingService()
    val repository: UserRepository = UserRepositoryImpl()

    route("auth"){

        post("login") {
            val loginRequest = call.receive<LoginRequest>()
            val user = repository.getUser(loginRequest.username, loginRequest.password)

            if (user == null) {
                call.respond(HttpStatusCode.Unauthorized,
                "Invalid credentials!")
                return@post
            }

            val token = jwtConfig.generateToken(JwtConfig.JwtUser(user.id, user.email))
            call.respond(hashMapOf("Token" to token))
        }
    }
}