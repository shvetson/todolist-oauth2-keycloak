package ru.shvets.todolist.auth

import ru.shvets.todolist.common.model.todo.Todo
import ru.shvets.todolist.common.model.todo.TodoId
import ru.shvets.todolist.common.permission.TodoPrincipalModel
import ru.shvets.todolist.common.permission.TodoPrincipalRelations

fun Todo.resolveRelationsTo(principal: TodoPrincipalModel): Set<TodoPrincipalRelations> = setOfNotNull(
    TodoPrincipalRelations.NONE,
    TodoPrincipalRelations.NEW.takeIf { id == TodoId.NONE },
    TodoPrincipalRelations.OWN.takeIf { principal.id == ownerId },
//    TodoPrincipalRelations.MODERATABLE.takeIf { visibility != TodoVisibility.VISIBLE_TO_OWNER },
//    TodoPrincipalRelations.PUBLIC.takeIf { visibility == TodoVisibility.VISIBLE_PUBLIC },
)