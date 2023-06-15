package ru.shvets.todolist.repo.psql

open class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5432/todo",
    val user: String = "postgres",
    val password: String = "postgres",
    val schema: String = "todo",
    // Удалять таблицы при старте - нужно для тестирования
    val dropDatabase: Boolean = false,
)