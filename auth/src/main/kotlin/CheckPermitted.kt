package ru.shvets.todolist.auth

import ru.shvets.todolist.common.model.todo.TodoCommand
import ru.shvets.todolist.common.permission.TodoPrincipalRelations
import ru.shvets.todolist.common.permission.TodoUserPermissions

fun checkPermitted(
    command: TodoCommand,
    relations: Iterable<TodoPrincipalRelations>,
    permissions: Iterable<TodoUserPermissions>,
) =
    relations.asSequence().flatMap { relation ->
        permissions.map { permission ->
            AccessTableConditions(
                command = command,
                permission = permission,
                relation = relation,
            )
        }
    }.any {
        accessTable[it] != null
    }

private data class AccessTableConditions(
    val command: TodoCommand,
    val permission: TodoUserPermissions,
    val relation: TodoPrincipalRelations,
)

private val accessTable = mapOf(
    // Create
    AccessTableConditions(
        command = TodoCommand.CREATE,
        permission = TodoUserPermissions.CREATE_OWN,
        relation = TodoPrincipalRelations.NEW,
    ) to true,

    // Read
    AccessTableConditions(
        command = TodoCommand.READ,
        permission = TodoUserPermissions.READ_OWN,
        relation = TodoPrincipalRelations.OWN,
    ) to true,
    AccessTableConditions(
        command = TodoCommand.READ,
        permission = TodoUserPermissions.READ_PUBLIC,
        relation = TodoPrincipalRelations.PUBLIC,
    ) to true,

    // Update
    AccessTableConditions(
        command = TodoCommand.UPDATE,
        permission = TodoUserPermissions.UPDATE_OWN,
        relation = TodoPrincipalRelations.OWN,
    ) to true,

    // Delete
    AccessTableConditions(
        command = TodoCommand.DELETE,
        permission = TodoUserPermissions.DELETE_OWN,
        relation = TodoPrincipalRelations.OWN,
    ) to true,
)