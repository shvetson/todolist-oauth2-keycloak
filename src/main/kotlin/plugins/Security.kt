package plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import authentication.token.JwtConfig

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  11.05.2023 08:06
 */

fun Application.configureSecurity() {
    val jwtConfig = JwtConfig()

    authentication {
        jwt {
            jwtConfig.configureKtorFeature(this)
        }
    }
}