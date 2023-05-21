package ru.shvets.todolist.app.routes

import io.konform.validation.Invalid
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.shvets.todolist.app.authentication.hashing.SHA256HashingService
import ru.shvets.todolist.app.authentication.hashing.SaltedHash
import ru.shvets.todolist.app.authentication.token.JwtConfig
import ru.shvets.todolist.common.models.user.User
import ru.shvets.todolist.common.repo.user.AuthRequest
import ru.shvets.todolist.common.repo.user.IUserRepository
import ru.shvets.todolist.app.repositories.UserRepository
import ru.shvets.todolist.common.validate.resultErrorValidation

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  10.05.2023 16:41
 */

fun Route.authRouting() {
    val jwtConfig = JwtConfig()
    val hashing = SHA256HashingService()
    val repository: IUserRepository = UserRepository()

    route("auth") {

        post("signup") {
            val request = call.receive<AuthRequest>()
            val validationResult = AuthRequest(request.username, request.password).validate()

            if (validationResult is Invalid<AuthRequest>) {
                call.respond(
                    HttpStatusCode.Conflict,
                    resultErrorValidation(validationResult)
                )
                return@post
            }

            if (repository.getUserByEmail(request.username) != null) {
                call.respond(
                    HttpStatusCode.Conflict,
                    "Username is already exists"
                )
                return@post
            }

            val saltedHash = hashing.generateSaltedHash(request.password)
            val user = User(
                email = request.username,
                password = saltedHash.hash,
                salt = saltedHash.salt
            )

            val wasAcknowledged = repository.addUser(user)

            if (wasAcknowledged == null) {
                call.respond(HttpStatusCode.Conflict)
                return@post
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    "User was added successfully!"
                )
            }
        }

        post("/signin") {
            val request = call.receive<AuthRequest>()
            val username = request.username
            val password = request.password
            val validationResult = AuthRequest(username, password).validate()

            if (validationResult is Invalid<AuthRequest>) {
                call.respond(
                    HttpStatusCode.Conflict,
                    resultErrorValidation(validationResult)
                )
                return@post
            }

            val user = repository.getUserByEmail(request.username)

            if (user == null) {
                call.respond(
                    HttpStatusCode.Conflict,
                    "Incorrect username or password"
                )
                return@post
            }

            val isValidPassword = hashing.verify(
                value = request.password,
                saltedHash = SaltedHash(
                    hash = user.password,
                    salt = user.salt
                )
            )

            if (!isValidPassword) {
                call.respond(
                    HttpStatusCode.Conflict,
                    "Incorrect username or password"
                )
                return@post
            }

            val token = jwtConfig.generateToken(JwtConfig.JwtUser(user.id, user.email))
            call.respond(
                HttpStatusCode.OK,
                hashMapOf("Token" to token)
            )
        }

        authenticate {
            get("user") {
                val principal = call.principal<JWTPrincipal>()
                val userName = principal?.getClaim("userName", String::class)
                call.respond(
                    HttpStatusCode.OK,
                    "User : $userName"
                )
            }
        }
    }
}