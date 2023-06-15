package ru.shvets.todolist.common.repo.todo

import ru.shvets.todolist.common.model.todo.TodoError

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  13.05.2023 22:59
 */

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<TodoError>
}