package plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import routes.authRouting
import routes.todosRouting

fun Application.configureRouting() {
    routing {
        authRouting()

        authenticate {
            todosRouting()
        }
    }
}