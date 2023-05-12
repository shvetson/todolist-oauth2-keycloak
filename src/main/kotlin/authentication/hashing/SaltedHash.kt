package ru.shvets.security.hashing

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  28.03.2023 09:58
 */

data class SaltedHash(
    val hash: String,
    val salt: String
)
