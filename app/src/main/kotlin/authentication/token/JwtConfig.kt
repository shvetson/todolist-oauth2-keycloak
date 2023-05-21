package ru.shvets.todolist.app.authentication.token

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.response.*
import java.util.*

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  10.05.2023 15:00
 */

class JwtConfig() {

    private val config = HoconApplicationConfig(ConfigFactory.load())
    private val jwtSecret = config.property("jwt.secret").getString()
    private val jwtIssuer = config.property("jwt.issuer").getString()
    private val jwtAudience = config.property("jwt.audience").getString()
    private val jwtRealm = config.property("jwt.realm").getString()
    private val jwtExpirationPeriod = config.property("jwt.expiration_time").getString().toLong()

    private val jwtAlgorithm = Algorithm.HMAC512(jwtSecret)

    fun generateToken(user: JwtUser): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(jwtIssuer)
        .withAudience(jwtAudience)
        .withClaim(CLAIM_USERID, user.userId)
        .withClaim(CLAIM_USERNAME, user.userName)
        .withExpiresAt(getExpirationTime())
        .sign(jwtAlgorithm)

    private val jwtVerifier: JWTVerifier = JWT
        .require(jwtAlgorithm)
        .withIssuer(jwtIssuer)
        .withAudience(jwtAudience)
        .build()

    fun configureKtorFeature(config: JWTAuthenticationProvider.Config) = with(config) {
        verifier(jwtVerifier)
        realm = jwtRealm

        validate {credential ->
            val userId = credential.payload.getClaim(CLAIM_USERID).asInt()
            val userName = credential.payload.getClaim(CLAIM_USERNAME).asString()

            if (userId != null && userName != null) {
                JWTPrincipal(credential.payload)
//                JwtUser(userId, userName)
            } else {
                null
            }
        }

        challenge { defaultScheme, realm ->
            call.respond(HttpStatusCode.Unauthorized,
                hashMapOf("Error" to "Token is not valid or has expired"))
        }

    }

    private fun getExpirationTime() = Date(System.currentTimeMillis() + jwtExpirationPeriod)

    companion object Constants {
        // claims
        private const val CLAIM_USERID = "userId"
        private const val CLAIM_USERNAME = "userName"
    }

    data class JwtUser(
        val userId: Long,
        val userName: String,
    ) : Principal
}