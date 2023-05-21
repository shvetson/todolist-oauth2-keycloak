package ru.shvets.todolist.app.repositories

import org.jetbrains.exposed.sql.*
import ru.shvets.todolist.app.database.DatabaseFactory.dbQuery
import ru.shvets.todolist.app.entities.UsersTable
import ru.shvets.todolist.common.models.user.User
import ru.shvets.todolist.common.repo.user.IUserRepository

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  11.05.2023 21:14
 */

class UserRepository : IUserRepository {

    override suspend fun getUser(username: String, password: String): User? = dbQuery {
        UsersTable.select { UsersTable.email.eq(username).and(UsersTable.password.eq(password)) }
            .map { it -> rowToUser(it) }
            .singleOrNull()
    }

    override suspend fun getAllUsers(): List<User> = dbQuery {
        UsersTable.selectAll()
            .map { it -> rowToUser(it) }
    }

    override suspend fun getUserByEmail(email: String): User? = dbQuery {
        UsersTable.select { UsersTable.email eq email }
            .mapNotNull { it -> rowToUser(it) }
            .singleOrNull()
    }

    override suspend fun addUser(user: User): User? = dbQuery {
        UsersTable.insert { it ->
            it[UsersTable.email] = user.email
            it[UsersTable.password] = user.password
            it[UsersTable.salt] = user.salt
        }.resultedValues?.singleOrNull()?.let(::rowToUser)
    }

    private fun rowToUser(row: ResultRow): User =
        User(
            id = row[UsersTable.id],
            email = row[UsersTable.email],
            password = row[UsersTable.password],
            salt = row[UsersTable.salt],
            active = row[UsersTable.active],
        )
}