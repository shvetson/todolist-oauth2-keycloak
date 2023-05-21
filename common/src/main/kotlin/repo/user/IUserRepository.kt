package ru.shvets.todolist.common.repo.user

import ru.shvets.todolist.common.models.user.User

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  10.05.2023 16:43
 */

interface IUserRepository {
    suspend fun getUser(username: String, password: String): User?
    suspend fun getAllUsers(): List<User>
    suspend fun getUserByEmail(email: String): User?
    suspend fun addUser(user: User): User?
}