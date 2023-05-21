package ru.shvets.todolist.app.authentication.hashing

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 09:58
 */

@Serializable
data class SaltedHash(
    val hash: String,
    val salt: String
)
