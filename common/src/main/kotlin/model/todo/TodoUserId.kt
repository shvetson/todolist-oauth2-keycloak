package ru.shvets.todolist.common.model.todo

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  13.03.2023 13:13
 */

@Serializable
@JvmInline
value class TodoUserId(private val id: String){
    fun asString() = id

    companion object {
        val NONE = TodoUserId("")
    }
}