package ru.shvets.todolist.app.plugin

import com.auth0.jwt.JWT
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import ru.shvets.todolist.app.TodoAppSettings
import ru.shvets.todolist.app.authentication.base.KtorAuthConfig.Companion.GROUPS_CLAIM
import ru.shvets.todolist.app.authentication.base.resolveAlgorithm

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  11.05.2023 08:06
 */

fun Application.configureSecurity(appSettings: TodoAppSettings) {

    install(Authentication) {
        jwt("auth-jwt") {
            val authConfig = appSettings.auth
            realm = authConfig.realm

            verifier {
                val algorithm = it.resolveAlgorithm(authConfig)
                JWT
                    .require(algorithm)
                    .withAudience(authConfig.audience)
                    .withIssuer(authConfig.issuer)
                    .build()
            }
            validate { jwtCredential: JWTCredential ->
                when {
                    jwtCredential.payload.getClaim(GROUPS_CLAIM).asList(String::class.java).isNullOrEmpty() -> {
                        this@configureSecurity.log.error("Groups claim must not be empty in JWT token")
                        null
                    }

                    else -> JWTPrincipal(jwtCredential.payload)
                }
            }
        }
    }
}
