package ru.shvets.todolist.authentication

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  10.05.2023 16:39
 */

@Serializable
data class LoginRequest(
    val username: String,
    val password: String,
)