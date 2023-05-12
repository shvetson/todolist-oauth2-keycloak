package ru.shvets.todolist.repositories

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import ru.shvets.todolist.database.DatabaseFactory.dbQuery
import ru.shvets.todolist.entities.Users
import ru.shvets.todolist.models.User

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  11.05.2023 21:14
 */

class PostgresUserRepository : UserRepository {

    override suspend fun getUser(username: String, password: String): User? = dbQuery{
        Users.select { Users.email.eq(username).and(Users.password.eq(password)) }
            .map {it -> rowToUser(it)}
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

    private fun rowToUser(row: ResultRow): User =
        User(
            id = row[Users.id],
            email = row[Users.email],
            password = row[Users.password],
            active = row[Users.active]
        )
}