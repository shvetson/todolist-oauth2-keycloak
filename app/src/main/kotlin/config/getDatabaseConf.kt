package ru.shvets.todolist.app.config

import io.ktor.server.application.*
import ru.shvets.todolist.repo.psql.TodoRepository
import ru.shvets.todolist.common.repo.todo.ITodoRepository
import ru.shvets.todolist.repo.psql.SqlProperties

fun Application.getDatabaseConf(type: DbType): ITodoRepository {

    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()

    return when (dbSetting) {
//        "in-memory", "inmemory", "memory", "mem" -> initInMemory()
        "postgres", "postgresql", "pg", "sql", "psql" -> initPostgres()
//        "cassandra", "nosql", "cass" -> initCassandra()
//        "arcade", "arcadedb", "graphdb", "gremlin", "g", "a" -> initGremliln()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.yml to one of: " +
                    "'inmemory', 'postgres', 'cassandra', 'gremlin'"
        )
    }
}

private fun Application.initPostgres(): ITodoRepository {
    val config = PostgresConfig(environment.config)
    return TodoRepository(
        properties = SqlProperties(
            url = config.url,
            user = config.user,
            password = config.password,
            schema = config.schema,
        )
    )
}

//private fun Application.initCassandra(): ITodoRepository {
//    val config = CassandraConfig(environment.config)
//    return RepoCassandra(
//        keyspaceName = config.keyspace,
//        host = config.host,
//        port = config.port,
//        user = config.user,
//        pass = config.pass,
//    )
//}

//private fun Application.initGremliln(): ITodoRepository {
//    val config = GremlinConfig(environment.config)
//    return RepoGremlin(
//        hosts = config.host,
//        port = config.port,
//        user = config.user,
//        pass = config.pass,
//        enableSsl = config.enableSsl,
//    )
//}

//private fun Application.initInMemory(): ITodoRepository {
//    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
//        Duration.parse(it)
//    }
//    return RepoInMemory(ttl = ttlSetting ?: 10.minutes)
//}

enum class DbType(val confName: String) {
    PROD("prod"), TEST("test")
}