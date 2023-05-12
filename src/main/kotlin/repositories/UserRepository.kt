package ru.shvets.todolist.repositories

import ru.shvets.todolist.models.User

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  10.05.2023 16:43
 */

interface UserRepository {
    suspend fun getUser(username: String, password: String): User?
    suspend fun getAllUsers(): List<User>
    suspend fun getUserByEmail(email: String): User?
    suspend fun addUser(user: User): User?
}