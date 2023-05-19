package models

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  11.05.2023 21:12
 */

@Serializable
data class User(
    val id: Long = 0L,
    val email: String,
    val password: String,
    val salt: String,
    val active: Boolean = true,
)