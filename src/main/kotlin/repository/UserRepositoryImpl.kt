package ru.shvets.todolist.repository

import org.jetbrains.exposed.sql.*
import ru.shvets.todolist.database.DatabaseFactory.dbQuery
import ru.shvets.todolist.entities.Users
import ru.shvets.todolist.models.User

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  11.05.2023 21:14
 */

class UserRepositoryImpl : UserRepository {

    override suspend fun getUser(username: String, password: String): User? = dbQuery {
        Users.select { Users.email.eq(username).and(Users.password.eq(password)) }
            .map { it -> rowToUser(it) }
            .singleOrNull()
    }

    override suspend fun getAllUsers(): List<User> = dbQuery {
        Users.selectAll()
            .map { it -> rowToUser(it) }
    }

    override suspend fun getUserByEmail(email: String): User? = dbQuery {
        Users.select { Users.email eq email }
            .mapNotNull { it -> rowToUser(it) }
            .singleOrNull()
    }

    override suspend fun addUser(user: User): User? = dbQuery {
        Users.insert { it ->
            it[Users.email] = user.email
            it[Users.password] = user.password
            it[Users.salt] = user.salt
        }.resultedValues?.singleOrNull()?.let(::rowToUser)
    }

    private fun rowToUser(row: ResultRow): User =
        User(
            id = row[Users.id],
            email = row[Users.email],
            password = row[Users.password],
            salt = row[Users.salt],
            active = row[Users.active],
        )
}