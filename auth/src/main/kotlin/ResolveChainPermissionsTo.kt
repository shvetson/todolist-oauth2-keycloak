package ru.shvets.todolist.auth

import ru.shvets.todolist.common.permission.TodoUserGroups
import ru.shvets.todolist.common.permission.TodoUserPermissions

fun resolveChainPermissions(
    groups: Iterable<TodoUserGroups>,
) = mutableSetOf<TodoUserPermissions>()
    .apply {
        addAll(groups.flatMap { groupPermissionsAdmits[it] ?: emptySet() })
        removeAll(groups.flatMap { groupPermissionsDenys[it] ?: emptySet() }.toSet())
    }
    .toSet()

private val groupPermissionsAdmits = mapOf(
    TodoUserGroups.USER to setOf(
        TodoUserPermissions.READ_OWN,
        TodoUserPermissions.READ_PUBLIC,
        TodoUserPermissions.CREATE_OWN,
        TodoUserPermissions.UPDATE_OWN,
        TodoUserPermissions.DELETE_OWN,
        TodoUserPermissions.OFFER_FOR_OWN,
    ),
    TodoUserGroups.MODERATOR_TODO to setOf(),
    TodoUserGroups.ADMIN_TODO to setOf(),
    TodoUserGroups.TEST to setOf(),
    TodoUserGroups.BAN_TODO to setOf(),
)

private val groupPermissionsDenys = mapOf(
    TodoUserGroups.USER to setOf(),
    TodoUserGroups.MODERATOR_TODO to setOf(),
    TodoUserGroups.ADMIN_TODO to setOf(),
    TodoUserGroups.TEST to setOf(),
    TodoUserGroups.BAN_TODO to setOf(
        TodoUserPermissions.UPDATE_OWN,
        TodoUserPermissions.CREATE_OWN,
    ),
)
