package ru.shvets.todolist.app.database

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import ru.shvets.todolist.app.entities.TodosTable
import ru.shvets.todolist.app.entities.UsersTable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  08.05.2023 12:09
 */

object DatabaseFactory {

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val driver = appConfig.property("db.driver").getString()
    private val url = appConfig.property("db.url").getString()
    private val user = appConfig.property("db.username").getString()
    private val pass = appConfig.property("db.password").getString()

    val db = Database.connect(hikari())

    fun init() {
        transaction(db) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(TodosTable, UsersTable)
//            SchemaUtils.drop(ToDos, Users)
            SchemaUtils.createMissingTablesAndColumns(TodosTable, UsersTable)
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.apply {
            driverClassName = driver
            jdbcUrl = url
            username = user
            password = pass
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction(db) {
//                addLogger(StdOutSqlLogger)
                block()
            }
        }
}