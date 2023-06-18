package ru.shvets.todolist.auth

import ru.shvets.todolist.common.permission.TodoPermission
import ru.shvets.todolist.common.permission.TodoPrincipalRelations
import ru.shvets.todolist.common.permission.TodoUserPermissions

fun resolveFrontPermissions(
    permissions: Iterable<TodoUserPermissions>,
    relations: Iterable<TodoPrincipalRelations>,
) = mutableSetOf<TodoPermission>()
    .apply {
        for (permission in permissions) {
            for (relation in relations) {
                accessTable[permission]?.get(relation)?.let { this@apply.add(it) }
            }
        }
    }
    .toSet()

private val accessTable = mapOf(
    // READ
    TodoUserPermissions.READ_OWN to mapOf(
        TodoPrincipalRelations.OWN to TodoPermission.READ
    ),
    TodoUserPermissions.READ_GROUP to mapOf(
        TodoPrincipalRelations.GROUP to TodoPermission.READ
    ),
    TodoUserPermissions.READ_PUBLIC to mapOf(
        TodoPrincipalRelations.PUBLIC to TodoPermission.READ
    ),
    TodoUserPermissions.READ_CANDIDATE to mapOf(
        TodoPrincipalRelations.MODERATABLE to TodoPermission.READ
    ),

    // UPDATE
    TodoUserPermissions.UPDATE_OWN to mapOf(
        TodoPrincipalRelations.OWN to TodoPermission.UPDATE
    ),
    TodoUserPermissions.UPDATE_PUBLIC to mapOf(
        TodoPrincipalRelations.MODERATABLE to TodoPermission.UPDATE
    ),
    TodoUserPermissions.UPDATE_CANDIDATE to mapOf(
        TodoPrincipalRelations.MODERATABLE to TodoPermission.UPDATE
    ),

    // DELETE
    TodoUserPermissions.DELETE_OWN to mapOf(
        TodoPrincipalRelations.OWN to TodoPermission.DELETE
    ),
    TodoUserPermissions.DELETE_PUBLIC to mapOf(
        TodoPrincipalRelations.MODERATABLE to TodoPermission.DELETE
    ),
    TodoUserPermissions.DELETE_CANDIDATE to mapOf(
        TodoPrincipalRelations.MODERATABLE to TodoPermission.DELETE
    ),
)
