package ru.shvets.todolist.app.plugin

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import ru.shvets.todolist.app.TodoAppSettings
import ru.shvets.todolist.app.base.KtorAuthConfig
import ru.shvets.todolist.app.base.KtorAuthConfig.Companion.GROUPS_CLAIM
import ru.shvets.todolist.app.base.resolveAlgorithm

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
                makeJWTVerifier(algorithm, authConfig)
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

fun makeJWTVerifier(algorithm: Algorithm, authConfig: KtorAuthConfig): JWTVerifier =
    JWT.require(algorithm)
        .withIssuer(authConfig.issuer)
        .withAudience(authConfig.audience)
        .build()