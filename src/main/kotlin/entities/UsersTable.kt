package entities

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  11.05.2023 21:06
 */

object UsersTable: Table() {
    val id: Column<Long> = long("id").autoIncrement().uniqueIndex()
    val email: Column<String> = varchar("email", 100).uniqueIndex()
    val password: Column<String> = varchar("password", 100)
    val salt: Column<String> = varchar("salt", 100)
    val active: Column<Boolean> = bool("active").default(true)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}